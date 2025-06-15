package com.x1.frans.approval.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApprovalCategoryQueryMapper {
    void updateOrderStatusByApprovalId(@Param("approvalId") long approvalId,@Param("status") String approved);

    void updateReturnStatusByApprovalId(@Param("approvalId") long approvalId,@Param("status") String approved);

    void updatePurchaseOrderStatusByApprovalId(@Param("approvalId") long approvalId,@Param("status") String APPROVED);
}
