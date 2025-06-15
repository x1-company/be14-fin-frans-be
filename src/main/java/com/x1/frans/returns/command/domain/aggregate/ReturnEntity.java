package com.x1.frans.returns.command.domain.aggregate;

import com.x1.frans.exception.InvalidRejectConditionException;
import com.x1.frans.returns.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "`return`")
public class ReturnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReturnStatus status;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "pickup_date")
    private LocalDateTime pickupDate;

    @Column(name = "rejected_reason")
    private String rejectedReason;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "franchise_id")
    private Long franchiseId;

    public void reject(String reason) {
        if (status != ReturnStatus.WAITING_FOR_RECEIPT) {
            throw new InvalidRejectConditionException("해당 반품 상태에서는 반려할 수 없습니다.");
        }

        this.status = ReturnStatus.REJECTED;
        this.rejectedReason = reason;
    }

    public void markReviewComplete() {
        if (this.status != ReturnStatus.WAITING_FOR_RECEIPT) {
            throw new InvalidRejectConditionException("접수 대기 상태에서만 검토 완료로 변경할 수 있습니다.");
        }
        this.status = ReturnStatus.REVIEW_COMPLETED;
    }

    public void cancelReviewComplete() {
        if (this.status != ReturnStatus.REVIEW_COMPLETED) {
            throw new InvalidRejectConditionException("검토 완료 상태에서만 접수 대기로 변경할 수 있습니다.");
        }
        this.status = ReturnStatus.WAITING_FOR_RECEIPT;
    }

}
