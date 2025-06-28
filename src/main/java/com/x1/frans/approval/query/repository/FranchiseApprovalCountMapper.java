package com.x1.frans.approval.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface FranchiseApprovalCountMapper {

    int countInProgressOrderApprovals(@Param("userId") Long userId);
    int countInProgressReturnApprovals(@Param("userId") Long userId);

}
