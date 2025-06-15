package com.x1.frans.purchase.command.application.service;

import com.x1.frans.exception.*;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestCreateCommand;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestProductCreateCommand;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestProductUpdateCommand;
import com.x1.frans.purchase.command.application.service.dto.PurchaseRequestUpdateCommand;
import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestProductEntity;
import com.x1.frans.purchase.command.domain.repository.PurchaseRequestProductRepository;
import com.x1.frans.purchase.command.domain.repository.PurchaseRequestRepository;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.aggregate.HqUserDetailEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import com.x1.frans.user.command.repository.HqUserDetailCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PurchaseRequestCommandServiceImpl implements PurchaseRequestCommandService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final PurchaseRequestProductRepository purchaseRequestProductRepository;
    private final UserCommandRepository userCommandRepository;
    private final HqUserDetailCommandRepository hqUserDetailCommandRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PurchaseRequestCommandServiceImpl(PurchaseRequestRepository purchaseRequestRepository,
                                             PurchaseRequestProductRepository purchaseRequestProductRepository,
                                             UserCommandRepository userCommandRepository,
                                             HqUserDetailCommandRepository hqUserDetailCommandRepository,
                                             ProductRepository productRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.purchaseRequestProductRepository = purchaseRequestProductRepository;
        this.userCommandRepository = userCommandRepository;
        this.hqUserDetailCommandRepository = hqUserDetailCommandRepository;
        this.productRepository = productRepository;
    }

    // 영업팀 허용 부서 id (id: 2, 4, 5, 6 = 영업팀, 영업1팀, 영업2팀, 영업3팀)
    private static final List<Long> ALLOWED_DEPARTMENT_IDS = List.of(2L, 4L, 5L, 6L);

    /*** 구매 요청 작성 ***/
    @Override
    @Transactional
    public Long create(PurchaseRequestCreateCommand command, Long userId) {
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보 없음"));

        // 부서 체크
        HqUserDetailEntity hqDetail = hqUserDetailCommandRepository.findByUser(user)
                .orElseThrow(() -> new InvalidDepartmentException("부서 정보를 찾을 수 없습니다."));
        if (!ALLOWED_DEPARTMENT_IDS.contains(hqDetail.getDepartmentId())) {
            throw new InvalidDepartmentException("영업팀 소속만 구매 요청을 할 수 있습니다.");
        }

        // isRequested가 null이면 true로 세팅
        boolean isRequested = command.getIsRequested() == null ? true : command.getIsRequested();

        // 상태 결정
        PurchaseRequestStatus status = isRequested ? PurchaseRequestStatus.REQUEST_PENDING : PurchaseRequestStatus.DRAFT;

        // 상품 가격 * 수량 합산
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PurchaseRequestProductCreateCommand p : command.getProducts()) {
            ProductEntity product = productRepository.findById(p.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("상품이 존재하지 않습니다: " + p.getProductId()));
            totalAmount = totalAmount.add(product.getPurchasePrice().multiply(BigDecimal.valueOf(p.getQuantity())));
        }

        // 구매요청 생성
        PurchaseRequestEntity entity = PurchaseRequestEntity.builder()
                .code(generateNextPurchaseRequestCode())
                .title(command.getTitle())
                .description(command.getDescription())
                .requestedDeliveryDate(command.getRequestedDeliveryDate())
                .totalAmount(totalAmount)
                .status(status)
                .isRequested(isRequested)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        purchaseRequestRepository.save(entity);

        int idx = 1;
        for (PurchaseRequestProductCreateCommand p : command.getProducts()) {
            PurchaseRequestProductEntity product = PurchaseRequestProductEntity.builder()
                    .no(idx++)
                    .quantity(p.getQuantity())
                    .remarks(p.getRemarks())
                    .productId(p.getProductId())
                    .purchaseRequest(entity)
                    .build();
            purchaseRequestProductRepository.save(product);
        }

        return entity.getId();
    }


    /*** 구매 요청 수정 ***/
    @Override
    @Transactional
    public void update(Long purchaseRequestId, PurchaseRequestUpdateCommand command, Long userId) {
        PurchaseRequestEntity entity = purchaseRequestRepository.findById(purchaseRequestId)
                .orElseThrow(() -> new PurchaseRequestNotFoundException("구매요청 정보 없음"));

        // isRequested(임시저장) 값 처리 (null이면 true)
        boolean isRequested = command.getIsRequested() == null ? true : command.getIsRequested();

        // 이미 저장된 상태에서 임시저장으로 변경하려는 경우 예외 처리
        if (Boolean.TRUE.equals(entity.getIsRequested()) && !isRequested) {
            throw new CannotChangeToDraftExceptioin("저장(요청) 상태에서는 다시 임시저장 상태로 변경할 수 없습니다.");
        }

        // 상태 결정
        PurchaseRequestStatus status = isRequested ? PurchaseRequestStatus.REQUEST_PENDING : PurchaseRequestStatus.DRAFT;

        // 수정 정보 반영
        entity.updateMainInfo(command.getTitle(), command.getDescription(), command.getRequestedDeliveryDate());
        entity.setIsRequested(isRequested);
        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());

        // 기존 자재 목록 가져오기
        List<PurchaseRequestProductEntity> oldProducts = purchaseRequestProductRepository.findByPurchaseRequest(entity);
        Map<Long, PurchaseRequestProductEntity> oldMap = new HashMap<>();
        for (PurchaseRequestProductEntity p : oldProducts) {
            oldMap.put(p.getId(), p);
        }

        // 새 자재 목록
        int idx = 1;
        BigDecimal totalAmount = BigDecimal.ZERO;
        Set<Long> newIds = new HashSet<>();
        for (PurchaseRequestProductUpdateCommand dto : command.getProducts()) {
            ProductEntity product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("상품이 존재하지 않습니다: " + dto.getProductId()));

            totalAmount = totalAmount.add(product.getPurchasePrice().multiply(BigDecimal.valueOf(dto.getQuantity())));

            if (dto.getId() == null) {
                // 신규 자재
                PurchaseRequestProductEntity add = PurchaseRequestProductEntity.builder()
                        .no(idx++)
                        .quantity(dto.getQuantity())
                        .remarks(dto.getRemarks())
                        .productId(dto.getProductId())
                        .purchaseRequest(entity)
                        .build();
                purchaseRequestProductRepository.save(add);
            } else {
                // 기존 자재 수정
                PurchaseRequestProductEntity exist = oldMap.get(dto.getId());
                if (exist != null) {
                    exist = PurchaseRequestProductEntity.builder()
                            .id(dto.getId())
                            .no(idx++)
                            .quantity(dto.getQuantity())
                            .remarks(dto.getRemarks())
                            .productId(dto.getProductId())
                            .purchaseRequest(entity)
                            .build();
                    purchaseRequestProductRepository.save(exist);
                    newIds.add(dto.getId());
                }
            }
        }
        // 삭제: 기존 자재 중에 새 리스트에 없는 것 삭제
        for (PurchaseRequestProductEntity p : oldProducts) {
            if (!newIds.contains(p.getId())) {
                purchaseRequestProductRepository.delete(p);
            }
        }

        entity.setTotalAmount(totalAmount);
    }

    @Override
    @Transactional
    public void delete(Long purchaseRequestId, Long userId) {
        PurchaseRequestEntity entity = purchaseRequestRepository.findById(purchaseRequestId)
                .orElseThrow(() -> new PurchaseRequestNotFoundException("구매요청 정보 없음"));

        // 유저 정보 조회
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보 없음"));

        // 영업팀 부서 체크
        HqUserDetailEntity hqDetail = hqUserDetailCommandRepository.findByUser(user)
                .orElseThrow(() -> new InvalidDepartmentException("부서 정보를 찾을 수 없습니다."));
        if (!ALLOWED_DEPARTMENT_IDS.contains(hqDetail.getDepartmentId())) {
            throw new InvalidDepartmentException("영업팀 소속만 삭제할 수 있습니다.");
        }

        // 임시저장 또는 요청대기만 삭제 가능
        if (!(entity.getStatus() == PurchaseRequestStatus.DRAFT ||
                entity.getStatus() == PurchaseRequestStatus.REQUEST_PENDING)) {
            throw new InvalidPurchaseRequestStatusException("임시저장 또는 요청대기 상태만 삭제할 수 있습니다.");
        }

        // 자재 삭제
        purchaseRequestProductRepository.deleteByPurchaseRequest(entity);

        // 구매요청 삭제
        purchaseRequestRepository.delete(entity);
    }

    private String generateNextPurchaseRequestCode() {
        String lastCode = purchaseRequestRepository.findTopByOrderByIdDesc()
                .map(PurchaseRequestEntity::getCode)
                .orElse(null);
        if (lastCode == null || !lastCode.matches("PR-\\d{4}")) {
            return "PR-0001";
        }
        int lastNum = Integer.parseInt(lastCode.substring(3));
        int nextNum = lastNum + 1;
        return String.format("PR-%04d", nextNum);
    }
}