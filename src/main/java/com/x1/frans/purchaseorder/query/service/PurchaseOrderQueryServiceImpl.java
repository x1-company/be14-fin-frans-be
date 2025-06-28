package com.x1.frans.purchaseorder.query.service;

import com.x1.frans.purchaseorder.enums.PurchaseOrderStatus;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderProductDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderRequestPendingListDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderSimpleDto;
import com.x1.frans.purchaseorder.query.repository.PurchaseOrderQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {

    private final PurchaseOrderQueryMapper purchaseOrderQueryMapper;

    @Autowired
    public PurchaseOrderQueryServiceImpl(PurchaseOrderQueryMapper purchaseOrderQueryMapper) {
        this.purchaseOrderQueryMapper = purchaseOrderQueryMapper;
    }

    @Override
    public Page<PurchaseOrderSimpleDto> getDraftOrder(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<PurchaseOrderSimpleDto> content = purchaseOrderQueryMapper.selectDraft(limit, offset);
        int total = purchaseOrderQueryMapper.countDraft();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public PurchaseOrderDetailDto getDraftOrderDetail(Long id) {
        PurchaseOrderDetailDto dto = purchaseOrderQueryMapper.selectDraftDetailById(id);
        if (dto != null) {
            List<PurchaseOrderProductDetailDto> products = purchaseOrderQueryMapper.selectProductsByOrderId(id);
            dto.setProducts(products);
        }
        return dto;
    }

    @Override
    public Page<PurchaseOrderSimpleDto> getOrder(Long supplierId, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<PurchaseOrderSimpleDto> content = purchaseOrderQueryMapper.selectOrder(supplierId, limit, offset);
        int total = purchaseOrderQueryMapper.countOrder(supplierId);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public PurchaseOrderDetailDto getOrderDetail(Long id) {
        PurchaseOrderDetailDto dto = purchaseOrderQueryMapper.selectOrderDetailById(id);
        if (dto != null) {
            List<PurchaseOrderProductDetailDto> products = purchaseOrderQueryMapper.selectProductsByOrderId(id);
            dto.setProducts(products);
        }
        return dto;
    }

    @Override
    public Page<PurchaseOrderSimpleDto> getOrderByStatus(PurchaseOrderStatus status, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        String dbStatus = status.getLabel();
        List<PurchaseOrderSimpleDto> content = purchaseOrderQueryMapper.selectOrderByStatus(dbStatus, limit, offset);
        int total = purchaseOrderQueryMapper.countOrderByStatus(dbStatus);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PurchaseOrderSimpleDto> getOrderByCode(String code, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<PurchaseOrderSimpleDto> content = purchaseOrderQueryMapper.selectOrderByCode(code, limit, offset);
        int total = purchaseOrderQueryMapper.countOrderByCode(code);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PurchaseOrderSimpleDto> getOrderByTitle(String title, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<PurchaseOrderSimpleDto> content = purchaseOrderQueryMapper.selectOrderByTitle(title, limit, offset);
        int total = purchaseOrderQueryMapper.countOrderByTitle(title);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<PurchaseOrderRequestPendingListDto> getRequestPending(Long userId) {
        return purchaseOrderQueryMapper.getRequestPending(userId);
    }
}