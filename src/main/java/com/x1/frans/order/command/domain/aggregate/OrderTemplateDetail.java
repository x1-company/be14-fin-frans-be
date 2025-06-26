package com.x1.frans.order.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "order_template_detail")
public class OrderTemplateDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_template_id")
    private OrderTemplate orderTemplate;

}
