package com.x1.frans.purchaseorder.command.application.service;

import com.x1.frans.exception.*;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import com.x1.frans.purchase.command.domain.aggregate.PurchaseRequestEntity;
import com.x1.frans.purchase.command.domain.repository.PurchaseRequestProductRepository;
import com.x1.frans.purchase.command.domain.repository.PurchaseRequestRepository;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderProductCreateDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderProductUpdateDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderSaveRequestDto;
import com.x1.frans.purchaseorder.command.application.dto.PurchaseOrderUpdateRequestDto;
import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderEntity;
import com.x1.frans.purchaseorder.command.domain.aggregate.PurchaseOrderProductEntity;
import com.x1.frans.purchaseorder.command.domain.repository.PurchaseOrderRepository;
import com.x1.frans.purchaseorder.enums.PurchaseOrderStatus;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.supplier.command.domain.repository.SupplierCommandRepository;
import com.x1.frans.user.command.aggregate.HqUserDetailEntity;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.HqUserDetailCommandRepository;
import com.x1.frans.user.command.repository.UserCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderCommandServiceImpl implements PurchaseOrderCommandService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final PurchaseRequestProductRepository purchaseRequestProductRepository;
    private final UserCommandRepository userCommandRepository;
    private final HqUserDetailCommandRepository hqUserDetailCommandRepository;
    private final SupplierCommandRepository supplierCommandRepository;

    @Autowired
    public PurchaseOrderCommandServiceImpl(
            PurchaseOrderRepository purchaseOrderRepository,
            ProductRepository productRepository,
            PurchaseRequestRepository purchaseRequestRepository,
            PurchaseRequestProductRepository purchaseRequestProductRepository,
            UserCommandRepository userCommandRepository,
            HqUserDetailCommandRepository hqUserDetailCommandRepository,
            SupplierCommandRepository supplierCommandRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.purchaseRequestProductRepository = purchaseRequestProductRepository;
        this.userCommandRepository = userCommandRepository;
        this.hqUserDetailCommandRepository = hqUserDetailCommandRepository;
        this.supplierCommandRepository = supplierCommandRepository;
    }

    // 구매팀만 발주 가능
    private static final List<Long> ALLOWED_DEPARTMENT_IDS = List.of(1L);

    /** 발주 임시저장 */
    @Override
    @Transactional
    public Long saveDraft(PurchaseOrderSaveRequestDto dto, Long userId) {
        // 사용자/부서/구매팀 체크
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보 없음"));
        HqUserDetailEntity hqDetail = hqUserDetailCommandRepository.findByUser(user)
                .orElseThrow(() -> new InvalidDepartmentException("부서 정보 없음"));
        if (!ALLOWED_DEPARTMENT_IDS.contains(hqDetail.getDepartmentId())) {
            throw new InvalidDepartmentException("구매팀만 발주할 수 있습니다.");
        }

        // 공급처, 요청일자
        SupplierEntity supplier = supplierCommandRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("공급처 정보 없음"));
        LocalDate requestedDeliveryDate = dto.getRequestedDeliveryDate();

        PurchaseOrderEntity order = PurchaseOrderEntity.builder()
                .code(generateOrderCode())
                .supplier(supplier)
                .user(user)
                .status(PurchaseOrderStatus.DRAFT)
                .isRequested(false)
                .totalAmount(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .requestedDeliveryDate(requestedDeliveryDate)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        int idx = 1;
        for (PurchaseOrderProductCreateDto p : dto.getProducts()) {
            // 자재 정보
            ProductEntity product = productRepository.findById(p.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("자재 정보 없음"));
            // 공급처 일치
            if (!product.getSupplier().getId().equals(supplier.getId())) {
                throw new ProductSupplierMisMatchException("자재 공급처와 발주 공급처가 다릅니다.");
            }
            // 구매요청 정보 및 상태
            PurchaseRequestEntity purchaseRequest = purchaseRequestRepository.findById(p.getPurchaseRequestId())
                    .orElseThrow(() -> new PurchaseRequestNotFoundException("구매요청 없음"));
            if (purchaseRequest.getStatus() != PurchaseRequestStatus.APPROVED) {
                throw new InvalidPurchaseRequestStatusException("승인 완료된 구매요청만 발주할 수 있습니다.");
            }

            // 구매요청에 자재가 포함되어 있는지 체크
            boolean exists = purchaseRequestProductRepository.existsByPurchaseRequestIdAndProductId(
                    p.getPurchaseRequestId(), p.getProductId());
            if (!exists) {
                throw new InvalidPurchaseRequestProductException("해당 구매요청에 해당 자재가 존재하지 않습니다.");
            }

            BigDecimal itemTotal = product.getPurchasePrice().multiply(BigDecimal.valueOf(p.getQuantity()));
            total = total.add(itemTotal);

            PurchaseOrderProductEntity productEntity = PurchaseOrderProductEntity.builder()
                    .purchaseOrder(order)
                    .no(idx++)
                    .product(product)
                    .quantity(p.getQuantity())
                    .remarks(p.getRemarks())
                    .purchaseRequestId(p.getPurchaseRequestId())
                    .build();

            order.getPurchaseOrderProducts().add(productEntity);
        }

        order.setTotalAmount(total);

        purchaseOrderRepository.save(order);

        return order.getId();
    }

    /** 발주 임시저장 수정 */
    @Override
    @Transactional
    public void updateDraft(Long purchaseOrderId, PurchaseOrderUpdateRequestDto dto, Long userId) {
        // 1. 발주(PurchaseOrder) 존재 여부
        PurchaseOrderEntity order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new PurchaseRequestNotFoundException("발주 정보 없음"));

        // 2. 사용자(User) 존재 여부
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보 없음"));

        // 3. 부서(구매팀) 체크
        HqUserDetailEntity hqDetail = hqUserDetailCommandRepository.findByUser(user)
                .orElseThrow(() -> new InvalidDepartmentException("부서 정보 없음"));
        if (!ALLOWED_DEPARTMENT_IDS.contains(hqDetail.getDepartmentId())) {
            throw new InvalidDepartmentException("구매팀만 발주를 수정할 수 있습니다.");
        }

        // 4. 상태 체크 (이미 요청된 상태라면 임시저장 불가)
        if (Boolean.TRUE.equals(order.getIsRequested())) {
            throw new CannotChangeToDraftExceptioin("저장(요청) 상태에서는 다시 임시저장 상태로 변경할 수 없습니다.");
        }

        // 6. 요청일자 등 기본 정보 세팅
        order.setRequestedDeliveryDate(dto.getRequestedDeliveryDate());
        order.setUpdatedAt(LocalDateTime.now());

        // 7. 상품 전체 검증 및 업데이트
        List<PurchaseOrderProductEntity> oldProducts = order.getPurchaseOrderProducts();
        Map<Long, PurchaseOrderProductEntity> oldMap = oldProducts.stream()
                .filter(p -> p.getId() != null)
                .collect(Collectors.toMap(PurchaseOrderProductEntity::getId, p -> p));
        Set<Long> newIds = new HashSet<>();

        int idx = 1;
        BigDecimal total = BigDecimal.ZERO;
        List<PurchaseOrderProductEntity> newProductList = new ArrayList<>();

        SupplierEntity orderSupplier = order.getSupplier();

        for (PurchaseOrderProductUpdateDto p : dto.getProducts()) {
            // 8. 상품(Product) 존재 여부
            ProductEntity product = productRepository.findById(p.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("자재 정보 없음"));

            // 9. 상품의 공급처 일치 여부
            if (!product.getSupplier().getId().equals(orderSupplier.getId())) {
                throw new ProductSupplierMisMatchException("자재 공급처와 발주 공급처가 다릅니다.");
            }

            // 10. 구매요청(PurchaseRequest) 존재 여부
            PurchaseRequestEntity purchaseRequest = purchaseRequestRepository.findById(p.getPurchaseRequestId())
                    .orElseThrow(() -> new PurchaseRequestNotFoundException("구매요청 없음"));

            // 11. 구매요청 상태 체크
            if (purchaseRequest.getStatus() != PurchaseRequestStatus.APPROVED) {
                throw new InvalidPurchaseRequestStatusException("승인 완료된 구매요청만 발주할 수 있습니다.");
            }

            // 12. 구매요청-자재(PurchaseRequestProduct) 매핑 여부
            if (!purchaseRequestProductRepository.existsByPurchaseRequestIdAndProductId(p.getPurchaseRequestId(), p.getProductId())) {
                throw new InvalidPurchaseRequestProductException("구매요청에 해당 자재가 포함되어 있지 않습니다.");
            }

            PurchaseOrderProductEntity popEntity = null;
            if (p.getId() != null && oldMap.containsKey(p.getId())) {
                popEntity = oldMap.get(p.getId());
                popEntity.setProduct(product);
                popEntity.setQuantity(p.getQuantity());
                popEntity.setRemarks(p.getRemarks());
                popEntity.setNo(idx++);
                popEntity.setPurchaseRequestId(p.getPurchaseRequestId());
                newIds.add(p.getId());
            } else {
                popEntity = PurchaseOrderProductEntity.builder()
                        .purchaseOrder(order)
                        .no(idx++)
                        .product(product)
                        .quantity(p.getQuantity())
                        .remarks(p.getRemarks())
                        .purchaseRequestId(p.getPurchaseRequestId())
                        .build();
            }
            newProductList.add(popEntity);

            // 합계
            total = total.add(product.getPurchasePrice().multiply(BigDecimal.valueOf(p.getQuantity())));
        }

        // 13. 삭제된 상품 처리 (기존에 있던 상품 중 dto에 없는 상품 삭제)
        oldProducts.removeIf(old -> old.getId() != null && !newIds.contains(old.getId()));

        // 14. 상품 목록 교체
        order.getPurchaseOrderProducts().clear();
        order.getPurchaseOrderProducts().addAll(newProductList);

        // 15. 합계 적용
        order.setTotalAmount(total);

        // 16. 저장 (CascadeType.ALL 적용 시 order만 save)
        purchaseOrderRepository.save(order);
    }

    private String generateOrderCode() {
        Long maxId = purchaseOrderRepository.findTopByOrderByIdDesc().map(PurchaseOrderEntity::getId).orElse(0L);
        return String.format("PO%04d", maxId + 1);
    }
}