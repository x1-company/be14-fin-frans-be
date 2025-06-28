package com.x1.frans.product.query.service;

import com.x1.frans.exception.ProductNotFoundException;
import com.x1.frans.product.query.dto.ProductDetailDTO;
import com.x1.frans.product.query.dto.ProductListDTO;
import com.x1.frans.product.query.repository.ProductQueryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductQueryMapper productQueryMapper;

    @Autowired
    public ProductQueryServiceImpl (ProductQueryMapper productQueryMapper) {
        this.productQueryMapper = productQueryMapper;
    }

    @Override
    public List<ProductListDTO> getProductsByFilter (
            Long productTypeId, Long productGroupId, Long productAttributeId, Boolean isActive, Long supplierId) {
        List<ProductListDTO> products = productQueryMapper.selectByFilter (
                productTypeId, productGroupId, productAttributeId, isActive, supplierId);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException ("검색 조건에 해당하는 자재가 없습니다.");
//        }

        return products;
    }

    @Override
    public ProductListDTO getProductByCode (String code) {
        ProductListDTO product = productQueryMapper.selectByCode (code);
        if (product == null) {
            throw new ProductNotFoundException("검색 조건에 해당하는 자재가 없습니다.");
        }

        return product;
    }

    @Override
    public List<ProductListDTO> getProductsByName (String name) {
        List<ProductListDTO> products = productQueryMapper.selectByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("검색 조건에 해당하는 자재가 없습니다.");
        }

        return products;
    }

    @Override
    public List<ProductListDTO> getProductsBySupplierName (String supplierName) {
        List<ProductListDTO> products = productQueryMapper.selectBySupplierName(supplierName);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("검색 조건에 해당하는 자재가 없습니다.");
        }

        return products;
    }

    @Override
    public ProductDetailDTO getProductDetailById(Long id) {
        ProductDetailDTO detail = productQueryMapper.selectDetailById(id);
        if (detail == null) {
            throw new ProductNotFoundException("해당 ID의 자재를 찾을 수 없습니다.");
        }
        return detail;
    }
}
