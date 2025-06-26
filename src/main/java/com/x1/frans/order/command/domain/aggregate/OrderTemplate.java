package com.x1.frans.order.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "order_template")
public class OrderTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column
    private Long userId;

    @OneToMany(mappedBy = "orderTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderTemplateDetail> details = new ArrayList<>();

    public void addDetail(OrderTemplateDetail detail) {
        this.details.add(detail);
        detail.setOrderTemplate(this); // 연관관계 주인 설정
    }
}
