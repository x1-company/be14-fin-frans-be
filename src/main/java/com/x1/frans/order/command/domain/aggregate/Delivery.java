package com.x1.frans.order.command.domain.aggregate;

import com.x1.frans.user.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeliveryStatus status;

    @Column(name = "delivery_company")
    private String deliveryCompany;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "delivered_at")
    private LocalDate deliveredAt;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    public void updateDeliveryInfo(String deliveryCompany, String trackingNumber, String name, String phone) {
        this.deliveryCompany = deliveryCompany;
        this.trackingNumber = trackingNumber;
        this.name = name;
        this.phone = phone;
    }

    public void completeDelivery(LocalDate deliveredAt) {
        this.deliveredAt = deliveredAt;
        this.status = DeliveryStatus.DELIVERED; // 배송 완료 상태로 변경
    }

}
