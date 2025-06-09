package com.x1.frans.supplier.query.repository;

import com.x1.frans.supplier.query.dto.SupplierDetailDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupplierQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);

    SupplierDetailDTO getSupplierDetail(int supplierId);
}
