package com.x1.frans.purchase.query.service;

import com.x1.frans.exception.PurchaseRequestNotFoundException;
import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchase.query.dto.PurchaseRequestDetailDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestProductSimpleDto;
import com.x1.frans.purchase.query.dto.PurchaseRequestSimpleDto;
import com.x1.frans.purchase.query.repository.PurchaseRequestQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRequestQueryService {

    private final PurchaseRequestQueryMapper purchaseRequestQueryMapper;

    public Page<PurchaseRequestSimpleDto> getRequestsByStatus(PurchaseRequestStatus status, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByStatus(status.name());
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByStatus(
                status.name(), pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    public Page<PurchaseRequestSimpleDto> searchByTitle(String title, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByTitle(title);
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByTitle(
                title, pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    public Page<PurchaseRequestSimpleDto> getRequestsByCode(String code, Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByCode(code);
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByCode(
                code, pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    public PurchaseRequestDetailDto getDetail(Long id) {
        PurchaseRequestDetailDto dto = purchaseRequestQueryMapper.selectDetailById(id);
        if (dto == null) {
            throw new PurchaseRequestNotFoundException("구매요청을 찾을 수 없습니다.");
        }
        List<PurchaseRequestProductSimpleDto> products = purchaseRequestQueryMapper.selectProductsByRequestId(id);
        dto.setProducts(products);
        return dto;
    }

    public Page<PurchaseRequestSimpleDto> getDraftPurchaseRequests(Pageable pageable) {
        int total = purchaseRequestQueryMapper.countByStatus("임시저장");
        List<PurchaseRequestSimpleDto> list = purchaseRequestQueryMapper.selectByStatus(
                "임시저장", pageable.getPageSize(), (int) pageable.getOffset()
        );
        return new PageImpl<>(list, pageable, total);
    }

    public PurchaseRequestDetailDto getDraftDetail(Long id) {
        PurchaseRequestDetailDto dto = purchaseRequestQueryMapper.selectDetailById(id);
        if (dto == null || !"임시저장".equals(dto.getStatus())) {
            throw new PurchaseRequestNotFoundException("임시저장 구매요청을 찾을 수 없습니다.");
        }
        List<PurchaseRequestProductSimpleDto> products = purchaseRequestQueryMapper.selectProductsByRequestId(id);
        dto.setProducts(products);
        return dto;
    }
}