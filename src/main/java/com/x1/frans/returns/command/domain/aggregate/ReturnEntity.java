package com.x1.frans.returns.command.domain.aggregate;

import com.x1.frans.exception.InvalidOrderRejectConditionException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.order.command.domain.aggregate.Delivery;
import com.x1.frans.returns.enums.ReturnStatus;
import com.x1.frans.user.command.aggregate.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "return")
public class ReturnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_amonut", nullable = false)
    private BigDecimal totalAmonut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReturnStatus status;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "pickup_date")
    private LocalDateTime pickupDate;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id", nullable = false)
    private FranchiseEntity franchise;

    public void reject(String reason) {
        if (status != ReturnStatus.WAITING_FOR_RECEIPT) {
            throw new InvalidOrderRejectConditionException("해당 반품 상태에서는 반려할 수 없습니다.");
        }

        this.status = ReturnStatus.REJECTED;
        this.rejectedReason = reason;
    }

    public void markReviewComplete() {
        if (this.status != ReturnStatus.WAITING_FOR_RECEIPT) {
            throw new InvalidOrderRejectConditionException("접수 대기 상태에서만 검토 완료로 변경할 수 있습니다.");
        }
        this.status = ReturnStatus.REVIEW_COMPLETED;
    }

    public void cancelReviewComplete() {
        if (this.status != ReturnStatus.REVIEW_COMPLETED) {
            throw new InvalidOrderRejectConditionException("검토 완료 상태에서만 접수 대기로 변경할 수 있습니다.");
        }
        this.status = ReturnStatus.WAITING_FOR_RECEIPT;
    }

}
