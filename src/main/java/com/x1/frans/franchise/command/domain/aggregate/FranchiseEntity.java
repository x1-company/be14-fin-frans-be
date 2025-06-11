package com.x1.frans.franchise.command.domain.aggregate;

import com.x1.frans.user.command.aggregate.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "franchise")
public class FranchiseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @Column(name = "business_number", nullable = false)
    private String businessNumber;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "signed_at", nullable = false)
    private LocalDate signedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "manager_id")
    private Long managerId;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;
}
