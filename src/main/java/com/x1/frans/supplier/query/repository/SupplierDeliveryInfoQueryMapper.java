package com.x1.frans.supplier.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface SupplierDeliveryInfoQueryMapper {

    String findLatestCodeByPrefix(@Param("codePrefix") String codePrefix);

}
