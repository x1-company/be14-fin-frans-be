package com.x1.frans.order.command.domain.aggregate;

import com.x1.frans.exception.InvalidRejectConditionException;
import com.x1.frans.exception.OrderRejectReasonRequiredException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.order.command.domain.vo.OrderStatus;
import com.x1.frans.user.command.aggregate.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id", nullable = false)
    private FranchiseEntity franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public void reject(String reason) {
        if (status != OrderStatus.REVIEWING && status != OrderStatus.REVIEW_COMPLETED) {
            throw new InvalidRejectConditionException("해당 주문 상태에서는 반려할 수 없습니다.");
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new OrderRejectReasonRequiredException("반려 사유는 필수입니다.");
        }

        this.status = OrderStatus.REJECTED;
        this.rejectedReason = reason;
        this.rejectedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markReviewComplete() {
        if (this.status != OrderStatus.REVIEWING) {
            throw new InvalidRejectConditionException("검토중 상태에서만 검토 완료로 변경할 수 있습니다.");
        }
        this.status = OrderStatus.REVIEW_COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancelReviewComplete() {
        if (this.status != OrderStatus.REVIEW_COMPLETED) {
            throw new InvalidRejectConditionException("검토 완료 상태에서만 검토중으로 변경할 수 있습니다.");
        }
        this.status = OrderStatus.REVIEWING;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(OrderStatus newStatus) {
        if (!isValidTransition(this.status, newStatus)) {
            throw new InvalidRejectConditionException("현재 상태에서는 해당 상태로 변경할 수 없습니다.");
        }

        if (newStatus == OrderStatus.DELIVERING && this.delivery == null) {
            throw new InvalidRejectConditionException("배송 중 상태로 변경하려면 배송 정보가 등록되어 있어야 합니다.");
        }

        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus target) {
        return switch (current) {
            case APPROVED, DELIVERED -> Set.of(OrderStatus.DELIVERING).contains(target);
            case DELIVERING -> Set.of(OrderStatus.DELIVERED).contains(target);
            default -> false;
        };
    }

    public void assignDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
