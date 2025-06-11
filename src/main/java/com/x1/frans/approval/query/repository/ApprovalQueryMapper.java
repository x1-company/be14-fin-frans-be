package com.x1.frans.approval.query.repository;

import com.x1.frans.approval.query.dto.ApprovalListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApprovalQueryMapper  {

    List<ApprovalListDTO> getApprovalList(long userId);
}
