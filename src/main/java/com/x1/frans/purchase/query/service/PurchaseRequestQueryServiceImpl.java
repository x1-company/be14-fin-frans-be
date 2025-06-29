package com.x1.frans.purchase.query.service;

import com.x1.frans.exception.PurchaseRequestNotFoundException;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestProductSimpleDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import com.x1.frans.purchase.query.repository.PurchaseRequestQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseRequestQueryServiceImpl implements PurchaseRequestQueryService {

    private final PurchaseRequestQueryMapper purchaseRequestQueryMapper;

    @Autowired
    public PurchaseRequestQueryServiceImpl(PurchaseRequestQueryMapper purchaseRequestQueryMapper) {
        this.purchaseRequestQueryMapper = purchaseRequestQueryMapper;
    }

    @Override
    public Page<PurchaseRequestSimpleDto> getRequestsByStatus(PurchaseRequestStatus status, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByStatus(status.getLabel());
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByStatus(
                status.getLabel(), pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<PurchaseRequestSimpleDto> searchByTitle(String title, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByTitle(title);
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByTitle(
                title, pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<PurchaseRequestSimpleDto> getRequestsByCode(String code, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByCode(code);
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByCode(
                code, pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public PurchaseRequestDetailDto getDetail(Long id) {
        PurchaseRequestDetailDto dto = purchaseRequestQueryMapper.selectDetailById(id);
        if (dto == null) {
            throw new PurchaseRequestNotFoundException("구매요청을 찾을 수 없습니다.");
        }
        List<PurchaseRequestProductSimpleDto> products = purchaseRequestQueryMapper.selectProductsByRequestId(id);
        dto.setProducts(products);
        return dto;
    }

    @Override
    public Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable) {
        int total = purchaseRequestQueryMapper.countDrafts();
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectDrafts(
                pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public PurchaseRequestDetailDto getDraftDetail(Long id) {
        PurchaseRequestDetailDto dto = purchaseRequestQueryMapper.selectDraftDetailById(id);
        if (dto == null) {
            throw new PurchaseRequestNotFoundException("임시저장 구매요청을 찾을 수 없습니다.");
        }
        List<PurchaseRequestProductSimpleDto> products = purchaseRequestQueryMapper.selectProductsByRequestId(id);
        dto.setProducts(products);
        return dto;
    }

    @Override
    public Page<PurchaseRequestSimpleDto> getAllRequests(Pageable pageable) {
        int total = purchaseRequestQueryMapper.countAllRequests();
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectAllRequests(
                pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<PurchaseRequestSimpleDto> getRequestsBySupplierId(Long supplierId, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countBySupplierId(supplierId);
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectBySupplierId(
                supplierId, pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }
}