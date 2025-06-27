package com.x1.frans.supplier.query.repository;

import com.x1.frans.supplier.query.dto.MyInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupplierQueryMapper {

    MyInfoDTO getMyInfo(Long userId);
}
