package com.x1.frans.purchase.command.domain.aggregate;

import com.x1.frans.purchase.enums.PurchaseRequestStatus;
import com.x1.frans.purchase.enums.PurchaseRequestStatusConverter;
import com.x1.frans.user.command.aggregate.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_request")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "requested_delivery_date", nullable = false)
    private LocalDate requestedDeliveryDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false, length = 50)
    @Convert(converter = PurchaseRequestStatusConverter.class)
    private PurchaseRequestStatus status;

    @Column(name = "is_requested", nullable = false)
    private Boolean isRequested;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updateMainInfo(String title, String description, LocalDate requestedDeliveryDate) {
        this.title = title;
        this.description = description;
        this.requestedDeliveryDate = requestedDeliveryDate;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}