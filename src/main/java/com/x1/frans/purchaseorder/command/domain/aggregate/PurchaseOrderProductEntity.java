package com.x1.frans.purchaseorder.command.domain.aggregate;

import com.x1.frans.product.command.domain.aggregate.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_order_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no", nullable = false)
    private Integer no;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrderEntity purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "purchase_request_id", nullable = false)
    private Long purchaseRequestId;
}