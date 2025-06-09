package com.x1.frans.user.command.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "hq_user_detail")
public class HqUserDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "duty_id")
    private Long dutyId;
}
