package com.x1.frans.approval.command.application.service;

import com.x1.frans.approval.command.application.dto.*;
import com.x1.frans.approval.command.domain.repository.*;
import com.x1.frans.approval.command.domain.aggregate.*;
import com.x1.frans.approval.common.ApprovalLineStatus;
import com.x1.frans.approval.common.ApprovalLineType;
import com.x1.frans.approval.common.ApprovalStatus;
import com.x1.frans.approval.query.service.ApprovalQueryService;
import com.x1.frans.exception.ApprovalActionFailedException;
import com.x1.frans.exception.ApprovalLineNotFoundException;
import com.x1.frans.exception.ApprovalNotFoundException;
import com.x1.frans.exception.UserNotFoundException;
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

}
