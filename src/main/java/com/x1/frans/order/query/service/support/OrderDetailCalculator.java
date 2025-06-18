package com.x1.frans.order.query.service.support;

import com.x1.frans.order.query.dto.FranchiseOrderDetailDto;
import com.x1.frans.order.query.dto.HqOrderDetailDto;
import com.x1.frans.product.query.dto.ProductDetailDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderDetailCalculator {

    public void fillDetails(HqOrderDetailDto detail, List<ProductDetailDTO> products) {
        detail.setProducts(products);
        detail.setTotalQuantity(calculateTotalQuantity(products));
        detail.setTotalAmount(calculateTotalAmount(products));
    }

    public void frfillDetails(FranchiseOrderDetailDto detail, List<ProductDetailDTO> products) {
        detail.setProducts(products);
        detail.setTotalQuantity(calculateTotalQuantity(products));
        detail.setTotalAmount(calculateTotalAmount(products));
    }

    // 총 수량 계산
    private int calculateTotalQuantity(List<ProductDetailDTO> products) {
        int totalQty = 0;
        for (ProductDetailDTO p : products) {
            totalQty += p.getQuantity();
        }
        return totalQty;
    }

    // 총 금액 계산
    private BigDecimal calculateTotalAmount(List<ProductDetailDTO> products) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ProductDetailDTO p : products) {
            BigDecimal price = p.getSalePrice();
            int qty = p.getQuantity();
            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(qty)));
        }
        return totalAmount;
    }
}
