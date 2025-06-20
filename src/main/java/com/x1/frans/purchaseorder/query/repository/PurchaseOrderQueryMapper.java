package com.x1.frans.purchaseorder.query.repository;

import com.x1.frans.purchaseorder.query.dto.PurchaseOrderDraftListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseOrderQueryMapper {
    List<PurchaseOrderDraftListDto> selectDraftOrders();
}
