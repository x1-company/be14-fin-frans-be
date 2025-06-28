package com.x1.frans.product.query.repository;

import com.x1.frans.product.query.dto.ProductDetailDTO;
import com.x1.frans.product.query.dto.ProductListDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductQueryMapper {

    List<ProductListDTO> selectByFilter (
            Long productTypeId, Long productGroupId, Long productAttributeId, Boolean isActive, Long supplierId);

    ProductListDTO selectByCode (String code);

    List<ProductListDTO> selectByName (String name);

    List<ProductListDTO> selectBySupplierName(String supplierName);

    ProductDetailDTO selectDetailById(Long id);
}
