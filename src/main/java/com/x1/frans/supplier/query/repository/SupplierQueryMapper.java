package com.x1.frans.supplier.query.repository;

import com.x1.frans.supplier.query.dto.SupplierListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);

    List<SupplierListDTO> findSupplierList();
}
