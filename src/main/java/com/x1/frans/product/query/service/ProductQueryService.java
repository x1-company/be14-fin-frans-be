package com.x1.frans.product.query.service;

import com.x1.frans.product.query.dto.ProductDetailDTO;
import com.x1.frans.product.query.dto.ProductListDTO;
import java.util.List;

public interface ProductQueryService {

    // 자재 리스트 조회 (필터 적용)
    List<ProductListDTO> getProductsByFilter (
            Long productTypeId, Long productGroupId, Long productAttributeId, Boolean isActive);

    // 자재 리스트 조회 (자재 코드로 검색)
    ProductListDTO getProductByCode(String code);

    // 자재 리스트 조회 (자재명으로 검색)
    List<ProductListDTO> getProductsByName(String name);

    // 자재 리스트 조회 (공급처명으로 검색)
    List<ProductListDTO> getProductsBySupplierName(String supplierName);

    // 자제 상세 조회(자재별 재고 현황, 자재 상세 정보)
    ProductDetailDTO getProductDetailById(Long id);

}
