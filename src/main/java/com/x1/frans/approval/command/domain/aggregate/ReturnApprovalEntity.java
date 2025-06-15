package com.x1.frans.approval.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "return_approval")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnApprovalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "approval_id", referencedColumnName = "id"),
            @JoinColumn(name = "approval_degree", referencedColumnName = "degree")
    })
    private ApprovalEntity approval;

    @Column(name = "return_id", nullable = false)
    private Long returnId;

    public ReturnApprovalEntity(ApprovalEntity approval, Long returnId) {
        this.approval = approval;
        this.returnId = returnId;
    }
}
