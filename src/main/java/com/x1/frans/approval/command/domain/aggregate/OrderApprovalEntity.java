package com.x1.frans.approval.command.domain.aggregate;

import com.x1.frans.approval.common.ApprovalCategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_approval")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderApprovalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "approval_id", referencedColumnName = "id"),
            @JoinColumn(name = "approval_degree", referencedColumnName = "degree")
    })
    private ApprovalEntity approval;

    @Column(name = "orders_id", nullable = false)
    private Long ordersId;

    public OrderApprovalEntity(ApprovalEntity approval, Long ordersId) {
        this.approval = approval;
        this.ordersId = ordersId;
    }
}
