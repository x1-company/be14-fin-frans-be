package com.x1.frans.supplier.query.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SupplierQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);
}
