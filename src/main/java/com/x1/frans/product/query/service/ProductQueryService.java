package com.x1.frans.product.query.service;

import com.x1.frans.product.query.dto.ProductDetailDTO;
import com.x1.frans.product.query.dto.ProductListDTO;
import java.util.List;

public interface ProductQueryService {

    List<ProductListDTO> getProductsByFilter (
            Long productTypeId, Long productGroupId, Long productAttributeId, Boolean isActive, Long supplierId);

    ProductListDTO getProductByCode(String code);

    List<ProductListDTO> getProductsByName(String name);

    List<ProductListDTO> getProductsBySupplierName(String supplierName);

    ProductDetailDTO getProductDetailById(Long id);

}
