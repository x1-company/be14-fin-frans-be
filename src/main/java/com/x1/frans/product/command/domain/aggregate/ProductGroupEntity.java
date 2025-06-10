package com.x1.frans.product.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_group")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}