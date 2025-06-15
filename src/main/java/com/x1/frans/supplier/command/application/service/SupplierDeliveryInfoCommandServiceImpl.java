package com.x1.frans.supplier.command.application.service;

import com.x1.frans.exception.ProductNotFoundException;
import com.x1.frans.exception.SupplierNotFoundException;
import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import com.x1.frans.product.command.domain.repository.ProductRepository;
import com.x1.frans.product.command.domain.repository.SupplierRepository;
import com.x1.frans.purchase_order.command.domain.aggregate.PurchaseOrderEntity;
import com.x1.frans.purchase_order.command.domain.repository.PurchaseOrderRepository;
import com.x1.frans.supplier.command.application.dto.DeliveryInfoCreateRequestDTO;
import com.x1.frans.supplier.command.domain.aggregate.SupplierDeliveryDetail;
import com.x1.frans.supplier.command.domain.aggregate.SupplierDeliveryInfo;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.supplier.command.domain.repository.SupplierDeliveryDetailCommandRepository;

import com.x1.frans.supplier.command.domain.repository.SupplierDeliveryInfoCommandRepository;
import com.x1.frans.supplier.query.repository.SupplierDeliveryInfoQueryMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierDeliveryInfoCommandServiceImpl implements SupplierDeliveryInfoCommandService {

    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    private final SupplierDeliveryDetailCommandRepository supplierDeliveryDetailRepository;
    private final SupplierDeliveryInfoCommandRepository supplierDeliveryInfoCommandRepository;
    private final SupplierDeliveryInfoQueryMapper supplierDeliveryInfoQueryMapper;

    @Override
    @Transactional
    public Long createDeliveryInfo(Long supplierId, String supplierCode, DeliveryInfoCreateRequestDTO requestDTO) {

        SupplierEntity supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("존재하지 않는 공급처입니다."));

        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(requestDTO.getPurchaseOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 발주입니다."));

        String deliveryCode = generateNextDeliveryCode(supplierCode);

        SupplierDeliveryInfo deliveryInfo = new SupplierDeliveryInfo();
        deliveryInfo.setSupplier(supplier);
        deliveryInfo.setPurchaseOrder(purchaseOrder);
        deliveryInfo.setCode(deliveryCode);
        deliveryInfo.setExpectedDate(requestDTO.getExpectedDate());
        deliveryInfo.setDate(LocalDateTime.now());
        deliveryInfo.setDeliveryCompanyName(requestDTO.getDeliveryCompanyName());
        deliveryInfo.setVehicleNumber(requestDTO.getVehicleNumber());
        deliveryInfo.setTrackingNumber(requestDTO.getTrackingNumber());
        deliveryInfo.setCreatedAt(LocalDateTime.now());
        deliveryInfo.setUpdatedAt(LocalDateTime.now());

        List<SupplierDeliveryDetail> details = requestDTO.getItems().stream()
                .map(item -> {
                    ProductEntity product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 자재: " + item.getProductId()));

                    SupplierDeliveryDetail detail = new SupplierDeliveryDetail();
                    detail.setProduct(product);
                    detail.setQuantity(item.getQuantity());
                    detail.setDeliveryInfo(deliveryInfo);
                    return detail;
                }).collect(Collectors.toList());

        BigDecimal totalAmount = details.stream()
                .map(detail -> detail.getProduct().getSalePrice()
                        .multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        deliveryInfo.setTotalAmount(totalAmount);

        SupplierDeliveryInfo savedInfo = supplierDeliveryInfoCommandRepository.save(deliveryInfo);
        details.forEach(detail -> detail.setDeliveryInfo(savedInfo));
        supplierDeliveryDetailRepository.saveAll(details);

        return savedInfo.getId();
    }


    // 납품 코드 생성 메서드 (supplierCode 기반 코드 생성)
    private String generateNextDeliveryCode(String supplierCode) {
        String prefix = "DLI-" + supplierCode + "-";
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String codePrefix = prefix + datePart + "-";

        String latestCode = supplierDeliveryInfoQueryMapper.findLatestCodeByPrefix(codePrefix);

        int nextNumber = 1;
        if (latestCode != null && latestCode.length() >= codePrefix.length() + 4) {
            String lastNumberStr = latestCode.substring(latestCode.length() - 4);
            try {
                nextNumber = Integer.parseInt(lastNumberStr) + 1;
            } catch (NumberFormatException e) {
                log.warn("납품 코드 번호 파싱 실패, 기본값 1로 초기화. latestCode={}", latestCode, e);
            }
        }

        return codePrefix + String.format("%04d", nextNumber);

    }
}
