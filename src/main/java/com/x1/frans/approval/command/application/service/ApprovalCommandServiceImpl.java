package com.x1.frans.approval.command.application.service;

import com.x1.frans.approval.command.application.dto.*;
import com.x1.frans.approval.command.domain.repository.*;
import com.x1.frans.approval.command.domain.aggregate.*;
import com.x1.frans.approval.common.ApprovalLineStatus;
import com.x1.frans.approval.common.ApprovalLineType;
import com.x1.frans.approval.common.ApprovalStatus;
import com.x1.frans.approval.query.repository.ApprovalCategoryQueryMapper;
import com.x1.frans.approval.query.repository.ApprovalQueryMapper;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.exception.*;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class ApprovalCommandServiceImpl implements ApprovalCommandService {

    private final UserCommandRepository userCommandRepository;
    private final ApprovalCommandRepository approvalCommandRepository;
    private final ApprovalLineCommandRepository approvalLineCommandRepository;
    private final ApprovalQueryService approvalQueryService;
    private final ApprovalFileCommandRepository approvalFileCommandRepository;
    private final OrderApprovalCommandRepository orderApprovalCommandRepository;
    private final ReturnApprovalCommandRepository returnApprovalCommandRepository;
    private final PurchaseOrderApprovalCommandRepository purchaseOrderApprovalCommandRepository;
    private final ApprovalLineTemplateCommandRepository approvalLineTemplateCommandRepository;
    private final ApprovalLineTemplateDetailCommandRepository approvalLineTemplateDetailCommandRepository;
    private final ApprovalQueryMapper approvalQueryMapper;
    private final ApprovalCategoryQueryMapper approvalCategoryQueryMapper;

    @Transactional
    @Override
    public ApprovalResponseDTO createApproval(ApprovalCreateRequestDTO request, long userId) {

        // 사용자 조회
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("기안자 정보를 찾을 수 없습니다."));

        // 결재 코드 생성
        String newCode = generateApprovalCode();

        // 결재 엔티티 생성
        ApprovalEntity approval = new ApprovalEntity();
        approval.setTitle(request.getTitle());
        approval.setRemarks(request.getRemarks());
        approval.setStatus(request.getIsRequest() ? ApprovalStatus.IN_PROGRESS : ApprovalStatus.DRAFT);
        approval.setIsRequested(request.getIsRequest());
        approval.setUser(user); // 연관관계 설정
        approval.setCode(newCode);

        // 저장
        approvalCommandRepository.save(approval);



        // 결재 문서
        ApprovalDocumentDTO doc = request.getApprovalDocuments();

        switch (doc.getCategoryType()) {
            case ORDER -> {
                List<OrderApprovalEntity> orderDocs = doc.getDocumentIds().stream()
                        .map(docId -> new OrderApprovalEntity(approval, docId))
                        .toList();
                orderApprovalCommandRepository.saveAll(orderDocs);
            }

            case RETURN -> {
                List<ReturnApprovalEntity> returnDocs = doc.getDocumentIds().stream()
                        .map(docId -> new ReturnApprovalEntity(approval, docId))
                        .toList();
                returnApprovalCommandRepository.saveAll(returnDocs);
            }

            case PURCHASE_ORDER -> {
                List<PurchaseOrderApprovalEntity> purchaseOrderDocs = doc.getDocumentIds().stream()
                        .map(docId -> new PurchaseOrderApprovalEntity(approval, docId))
                        .toList();
                purchaseOrderApprovalCommandRepository.saveAll(purchaseOrderDocs);
            }

            default -> throw new IllegalArgumentException("알 수 없는 문서 유형입니다.");
        }

        // 결재선 처리
        List<ApprovalLineEntity> approvalLines = request.getApprovalLines().stream()
                .map(line -> {
                    UserEntity approver = userCommandRepository.findById(line.getUserId())
                            .orElseThrow(() -> new UserNotFoundException("결재자 정보를 찾을 수 없습니다."));

                    ApprovalLineEntity approvalLine = new ApprovalLineEntity();
                    approvalLine.setApproval(approval);
                    approvalLine.setUser(approver);
                    approvalLine.setSeq(line.getSeq());
                    approvalLine.setApprovalType(ApprovalLineType.valueOf(line.getType()));
                    approvalLine.setApprovalDegree(approval.getDegree().longValue());
                    return approvalLine;
                }).toList();


        // 순서가 필요한 라인만 필터링 (결재자, 협조자)
        List<ApprovalLineEntity> orderedLines = approvalLines.stream()
                .filter(line -> line.getApprovalType().isOrdered())
                .sorted(Comparator.comparingInt(ApprovalLineEntity::getSeq))
                .collect(toList());

        // 순서 필요 없는 나머지 (참조자, 수신자)
        List<ApprovalLineEntity> referenceLines = approvalLines.stream()
                .filter(line -> !line.getApprovalType().isOrdered())
                .collect(toList());

        // 상태 설정 enum 메서드에 위임
        for (int i = 0; i < orderedLines.size(); i++) {
            ApprovalLineEntity line = orderedLines.get(i);
            line.setStatus(line.getApprovalType().getInitialStatus(i));
        }


        // 결재선 저장
        List<ApprovalLineEntity> allLines = new ArrayList<>();
        allLines.addAll(orderedLines);
        allLines.addAll(referenceLines);

        // 참조자/수신자는 상태 없이 저장
        referenceLines.forEach(line -> line.setStatus(null));

        approvalLineCommandRepository.saveAll(allLines);


        // 첨부파일
        List<ApprovalFileEntity> fileEntities = request.getFiles()== null ? List.of() :
                request.getFiles().stream()
                .map(file -> {
                    ApprovalFileEntity approvalFile = new ApprovalFileEntity();
                    approvalFile.setApproval(approval);
                    approvalFile.setName(file.getName());
                    approvalFile.setUrl(file.getUrl());
                    return approvalFile;
                }).toList();

        approvalFileCommandRepository.saveAll(fileEntities);
        return new ApprovalResponseDTO(approval.getId(), approval.getCode(), approval.getCreatedAt());
}

    private String generateApprovalCode() {
        String prefix = "APP";
        String yearMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String codePrefix = prefix + yearMonth;

        String latestCode = approvalQueryService.findLatestApprovalCode(codePrefix);

        return generateSequentialCode(codePrefix, latestCode);
    }

    private String generateSequentialCode(String codePrefix, String latestCode) {
        int nextSeq = 1;

        if (latestCode != null && latestCode.startsWith(codePrefix)) {
            String seqStr = latestCode.substring(codePrefix.length());
            try {
                nextSeq = Integer.parseInt(seqStr) + 1;
            } catch (NumberFormatException e) {
                // 예외 발생 시 기본값 유지
            }
        }

        return String.format("%s%04d", codePrefix, nextSeq);
    }


    @Transactional
    @Override
    public Optional<ApprovalResponseDTO> approverApprove(ApprovalApproveRequestDTO request, long approvalId, long userId) {

        // 결재 본문 조회
        ApprovalEntity approval = approvalCommandRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalNotFoundException("결재를 찾을 수 없습니다."));

        // 현재 사용자의 결재선 찾기
        ApprovalLineEntity currentLine = approvalLineCommandRepository.findByApprovalIdAndUserId(approval.getId(), userId)
                .orElseThrow(() -> new ApprovalLineNotFoundException("해당 사용자의 결재선이 없습니다."));

        if (!currentLine.getStatus().equals(ApprovalLineStatus.WAITING)) {
            throw new ApprovalActionFailedException("결재선이 대기 상태가 아닙니다.");
        }

        // 결재 or 협조인 경우 → 상태를 승인으로 변경
        if ("결재".equals(request.getApprovalType()) || "협조".equals(request.getApprovalType())) {
            currentLine.setStatus(ApprovalLineStatus.APPROVED);
            currentLine.setOpinion(request.getOpinion());
            currentLine.setProcessedAt(LocalDateTime.now());

            approvalLineCommandRepository.save(currentLine);
        }

        // 다음 결재선 대상 찾기
        int nextSeq = currentLine.getSeq() + 1;
        Optional<ApprovalLineEntity> nextLineOpt = approvalLineCommandRepository.findByApprovalIdAndSeq(approval.getId(), nextSeq);

        // 다음 결재선이 있으면 상태를 EXPECTED → WAITING 으로 변경
        nextLineOpt.ifPresent(nextLine -> {
            if (nextLine.getStatus() == ApprovalLineStatus.EXPECTED) {
                nextLine.setStatus(ApprovalLineStatus.WAITING);
                approvalLineCommandRepository.save(nextLine);
            }
        });

        // 다음 결재선이 없을때 결재 완료 처리
        if (nextLineOpt.isEmpty()) {
            // 결재 상태 변경
            approval.setStatus(ApprovalStatus.APPROVED);
            approval.setProcessedAt(LocalDateTime.now());
            approvalCommandRepository.save(approval);

            // 결재 카테고리 조회
            String category = approvalQueryMapper.findCategoryByApprovalId(approval.getId());

            switch (category) {
                case "ORDER" -> {
                    approvalCategoryQueryMapper.updateOrderStatusByApprovalId(approvalId,"APPROVED");
                }
                case "RETURN" -> {
                    approvalCategoryQueryMapper.updateReturnStatusByApprovalId(approvalId,"APPROVED");
                }
                case "PURCHASE_ORDER" -> {
                    approvalCategoryQueryMapper.updatePurchaseOrderStatusByApprovalId(approvalId,"APPROVED");
                }
                default -> {
                    throw new IllegalStateException("알 수 없는 결재 카테고리입니다: " + category);
                }
            }
        }

        return Optional.of(new ApprovalResponseDTO(approval.getId(), approval.getCode(), approval.getCreatedAt()));
    }

    @Transactional
    @Override
    public Optional<ApprovalResponseDTO> approverReject(ApprovalRejectRequestDTO request, long approvalId, long userId) {

        // 결재 본문 조회
        ApprovalEntity approval = approvalCommandRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalNotFoundException("결재를 찾을 수 없습니다."));

        // 현재 사용자의 결재선 찾기
        ApprovalLineEntity line = approvalLineCommandRepository.findByApprovalIdAndUserId(approval.getId(), userId)
                .orElseThrow(() -> new ApprovalLineNotFoundException("해당 사용자의 결재선이 없습니다."));

        if (!line.getStatus().equals(ApprovalLineStatus.WAITING)) {
            throw new ApprovalActionFailedException("결재선이 대기 상태가 아닙니다.");
        }


        // 결재 or 협조인 경우 → 상태를 반려로 변경
        if ("결재".equals(request.getApprovalType()) || "협조".equals(request.getApprovalType())) {
            line.setStatus(ApprovalLineStatus.REJECTED);
            line.setOpinion(request.getOpinion());
            line.setProcessedAt(LocalDateTime.now());

            approvalLineCommandRepository.save(line);
        }

            // 결재 상태 반려 처리
            approval.setStatus(ApprovalStatus.REJECTED);
            approvalCommandRepository.save(approval);

        return Optional.of(new ApprovalResponseDTO(approval.getId(), approval.getCode(), approval.getCreatedAt()));
    }

    @Transactional
    @Override
    public Optional<ApprovalResponseDTO> approvalLineTemplates(ApprovalLineTemplateCreateRequestDTO request, long userId) {

        UserEntity owner = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        ApprovalLineTemplateEntity template = new ApprovalLineTemplateEntity();
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setSeq(request.getSeq());
        template.setUser(owner);


        List<ApprovalLineTemplateDetailEntity> details = new ArrayList<>();

        for (ApprovalTemplateLineDTO dto : request.getLines()) {
            UserEntity approver = userCommandRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("결재선 사용자가 아닙니다."));

            ApprovalLineTemplateDetailEntity detail = new ApprovalLineTemplateDetailEntity();
            detail.setUser(approver);
            detail.setSeq(dto.getSeq());
            detail.setType(dto.getType());
            detail.setTemplate(template);

            details.add(detail);
        }

        template.setDetails(details);
        approvalLineTemplateCommandRepository.save(template);

        return Optional.of(new ApprovalResponseDTO(template.getId(), template.getName()));
    }

    @Transactional
    @Override
    public Optional<ApprovalResponseDTO> approvalLineTemplatesModify(ApprovalLineTemplateCreateRequestDTO request, long userId, Long templateId) {
        if (templateId == null) {
            throw new IllegalArgumentException("템플릿 ID가 없습니다.");
        }

        ApprovalLineTemplateEntity template = approvalLineTemplateCommandRepository.findById(templateId)
                .orElseThrow(() -> new ApprovalLineTemplateNotFoundException("템플릿을 찾을 수 없습니다."));

        // 본인 소유 템플릿인지 확인
        if (template.getUser() == null || !template.getUser().getId().equals(userId)) {
            throw new UnauthorizedTemplateAccessException("수정 권한이 없습니다.");
        }



        //  템플릿 정보 업데이트
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setSeq(request.getSeq());
        approvalLineTemplateCommandRepository.save(template);

        // 기존 상세 결재선 삭제
        approvalLineTemplateDetailCommandRepository.deleteByTemplateId(templateId);

        // 새 상세 결재선 등록
        List<ApprovalLineTemplateDetailEntity> newDetails = request.getLines().stream()
                .map(line -> {

                    ApprovalLineTemplateDetailEntity detail = new ApprovalLineTemplateDetailEntity();

                    UserEntity approver = userCommandRepository.findById(line.getUserId())
                            .orElseThrow(() -> new RuntimeException("결재선 사용자를 찾을 수 없습니다."));
                    detail.setUser(approver);
                    detail.setTemplate(template);
                    detail.setType(line.getType());
                    detail.setSeq(line.getSeq());
                    return detail;
                })
                .collect(Collectors.toList());
        approvalLineTemplateDetailCommandRepository.saveAll(newDetails);

        return Optional.of(new ApprovalResponseDTO(template.getId(), template.getName()));
    }

    @Transactional
    @Override
    public Optional<ApprovalResponseDTO> deleteApprovalLineTemplates(long userId, Long templateId) {
        if (templateId == null) {
            throw new IllegalArgumentException("템플릿 ID가 없습니다.");
        }

        ApprovalLineTemplateEntity template = approvalLineTemplateCommandRepository.findById(templateId)
                .orElseThrow(() -> new ApprovalLineTemplateNotFoundException("템플릿을 찾을 수 없습니다."));

        // 본인 소유 템플릿인지 확인
        if (template.getUser() == null || !template.getUser().getId().equals(userId)) {
            throw new UnauthorizedTemplateAccessException("수정 권한이 없습니다.");
        }
        approvalLineTemplateCommandRepository.delete(template);

        return Optional.of(new ApprovalResponseDTO(template.getId(), template.getName()));
    }

    @Transactional
    @Override
    public ApprovalResponseDTO modifyApproval(ApprovalCreateRequestDTO request, long userId, long approvalId) {

        // 결재 문서 조회
        ApprovalEntity approval = approvalCommandRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalNotFoundException("결재 문서를 찾을 수 없습니다."));

        // 기안자 조회
        if (!approval.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("결재 문서를 수정할 권한이 없습니다.");
        }



        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 필수 입력값입니다.");
        }
        if (request.getApprovalLines().isEmpty()) {
            throw new IllegalArgumentException("결재선은 최소 1명 이상이어야 합니다.");
        }
        if (approval.getDegree() == null) {
            throw new IllegalStateException("approval degree 값이 누락되었습니다.");
        }
        if (request.getApprovalDocuments() == null || request.getApprovalDocuments().getDocumentIds().isEmpty()) {
            throw new IllegalArgumentException("문서 정보가 비어 있습니다.");
        }


        approvalFileCommandRepository.deleteByApprovalId(approvalId);
        approvalLineCommandRepository.deleteByApprovalId(approvalId);
        returnApprovalCommandRepository.deleteByApprovalId(approvalId);


        approval.setTitle(request.getTitle());
        approval.setRemarks(request.getRemarks());

        if (request.getIsRequest()) {
            approval.setIsRequested(true); // 결재 진행중
            approval.setStatus(ApprovalStatus.IN_PROGRESS);

            // degree 증가 처리
            if (approval.getDegree() == null) {
                approval.setDegree(1); // 최초 등록
            } else {
                approval.setDegree(approval.getDegree() + 1); // 재기안
            }

        } else {
            approval.setIsRequested(false); // 임시저장
            approval.setStatus(ApprovalStatus.DRAFT);

            // 임시저장일 경우 degree를 0으로 설정 -기안 전 상태
            if (approval.getDegree() == null) {
                approval.setDegree(0);
            }
        }
        orderApprovalCommandRepository.deleteByApprovalId(approvalId);
        purchaseOrderApprovalCommandRepository.deleteByApprovalId(approvalId);




        // 결재 문서
        ApprovalDocumentDTO doc = request.getApprovalDocuments();

        switch (doc.getCategoryType()) {
            case ORDER -> {
                List<OrderApprovalEntity> orderDocs = doc.getDocumentIds().stream()
                        .map(docId -> new OrderApprovalEntity(approval, docId))
                        .toList();
                orderApprovalCommandRepository.saveAll(orderDocs);
            }

            case RETURN -> {
                List<ReturnApprovalEntity> returnDocs = doc.getDocumentIds().stream()
                        .map(docId -> new ReturnApprovalEntity(approval, docId))
                        .toList();
                returnApprovalCommandRepository.saveAll(returnDocs);
            }

            case PURCHASE_ORDER -> {
                List<PurchaseOrderApprovalEntity> purchaseOrderDocs = doc.getDocumentIds().stream()
                        .map(docId -> new PurchaseOrderApprovalEntity(approval, docId))
                        .toList();
                purchaseOrderApprovalCommandRepository.saveAll(purchaseOrderDocs);
            }

            default -> throw new IllegalArgumentException("알 수 없는 문서 유형입니다.");
        }

        // 결재선 처리
        List<ApprovalLineEntity> approvalLines = request.getApprovalLines().stream()
                .map(line -> {
                    UserEntity approver = userCommandRepository.findById(line.getUserId())
                            .orElseThrow(() -> new UserNotFoundException("결재자 정보를 찾을 수 없습니다."));

                    ApprovalLineEntity approvalLine = new ApprovalLineEntity();
                    approvalLine.setApproval(approval);
                    approvalLine.setUser(approver);
                    approvalLine.setSeq(line.getSeq());
                    approvalLine.setApprovalType(ApprovalLineType.valueOf(line.getType()));
                    if (approval.getDegree() != null) {
                        approvalLine.setApprovalDegree(approval.getDegree().longValue());
                    }
                    return approvalLine;
                }).toList();


        // 순서가 필요한 라인만 필터링 (결재자, 협조자)
        List<ApprovalLineEntity> orderedLines = approvalLines.stream()
                .filter(line -> line.getApprovalType().isOrdered())
                .sorted(Comparator.comparingInt(ApprovalLineEntity::getSeq))
                .collect(toList());

        // 순서 필요 없는 나머지 (참조자, 수신자)
        List<ApprovalLineEntity> referenceLines = approvalLines.stream()
                .filter(line -> !line.getApprovalType().isOrdered())
                .collect(toList());

        // 상태 설정 enum 메서드에 위임
        for (int i = 0; i < orderedLines.size(); i++) {
            ApprovalLineEntity line = orderedLines.get(i);
            line.setStatus(line.getApprovalType().getInitialStatus(i));
        }


        // 결재선 저장
        List<ApprovalLineEntity> allLines = new ArrayList<>();
        allLines.addAll(orderedLines);
        allLines.addAll(referenceLines);

        // 참조자/수신자는 상태 없이 저장
        referenceLines.forEach(line -> line.setStatus(null));

        approvalLineCommandRepository.saveAll(allLines);


        // 첨부파일
        List<ApprovalFileEntity> fileEntities = request.getFiles()== null ? List.of() :
                request.getFiles().stream()
                        .map(file -> {
                            ApprovalFileEntity approvalFile = new ApprovalFileEntity();
                            approvalFile.setApproval(approval);
                            approvalFile.setName(file.getName());
                            approvalFile.setUrl(file.getUrl());
                            return approvalFile;
                        }).toList();

        approvalFileCommandRepository.saveAll(fileEntities);


        return new ApprovalResponseDTO(approval.getId(), approval.getTitle());

    }

    @Transactional
    @Override
    public ApprovalResponseDTO updateRequestState(ApprovalStatusUpdateDTO request, long userId, long approvalId) {

        ApprovalEntity approval = approvalCommandRepository.findById(approvalId)
                .orElseThrow(() -> new ApprovalNotFoundException("결재문서가 존재하지 않습니다."));


        if (!approval.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException("결재 수정 권한 없습니다.");
        }

        approval.setIsRequested(request.getIsRequested());

        // 요청이 해제 (isRequested == false) → 상태를 DRAFT로 변경
        if (Boolean.FALSE.equals(request.getIsRequested())) {
            approval.setStatus(ApprovalStatus.DRAFT);
        }

        approvalCommandRepository.save(approval);


        return new ApprovalResponseDTO(approval.getId(), approval.getTitle());

    }

}
