package com.x1.frans.approval.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_order_approval")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderApprovalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "approval_id", referencedColumnName = "id"),
            @JoinColumn(name = "approval_degree", referencedColumnName = "degree")
    })
    private ApprovalEntity approval;

    @Column(name = "purchase_order_id", nullable = false)
    private Long purchaseOrderId;

    public PurchaseOrderApprovalEntity(ApprovalEntity approval, Long purchaseOrderId) {
        this.approval = approval;
        this.purchaseOrderId = purchaseOrderId;
    }
}
