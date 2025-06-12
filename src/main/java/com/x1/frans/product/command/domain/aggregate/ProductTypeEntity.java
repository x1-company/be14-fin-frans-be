package com.x1.frans.product.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_type")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}