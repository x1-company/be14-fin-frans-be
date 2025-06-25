package com.x1.frans.purchaseorder.query.repository;

import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderProductDetailDto;
import com.x1.frans.purchaseorder.query.dto.PurchaseOrderSimpleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseOrderQueryMapper {
    // 임시저장 목록
    List<PurchaseOrderSimpleDto> selectDraft(@Param("limit") Integer limit,
                                              @Param("offset") Integer offset);
    int countDraft();
    PurchaseOrderDetailDto selectDraftDetailById(@Param("id") Long id);

    // 발주 목록 (임시저장 제외, 공급처별)
    List<PurchaseOrderSimpleDto> selectOrder(@Param("supplierId") Long supplierId,
                                              @Param("limit") Integer limit,
                                              @Param("offset") Integer offset);
    int countOrder(@Param("supplierId") Long supplierId);

    // 발주 상세
    PurchaseOrderDetailDto selectOrderDetailById(@Param("id") Long id);

    // 공통 자재 목록
    List<PurchaseOrderProductDetailDto> selectProductsByOrderId(@Param("id") Long id);

    // 상태로 발주 목록 조회
    List<PurchaseOrderSimpleDto> selectOrderByStatus(
            @Param("status") String status,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );
    int countOrderByStatus(@Param("status") String status);
}