package com.x1.frans.supplier.query.repository;

import com.x1.frans.supplier.query.dto.SupplierDeliveryProductDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface SupplierDeliveryProductMapper {

    List<SupplierDeliveryProductDTO> findAllBySupplierId (@Param("supplierId") Long supplierId);

    List<SupplierDeliveryProductDTO> findProductByName (
            @Param("supplierId") Long supplierId,
            @Param("name") String name
    );

    List<SupplierDeliveryProductDTO> findProductByCode (
            @Param("supplierId") Long supplierId,
            @Param("code") String code
    );

    List<SupplierDeliveryProductDTO> findProductByNameAndCode(
            @Param("supplierId") Long supplierId,
            @Param("name") String name,
            @Param("code") String code
    );

}
