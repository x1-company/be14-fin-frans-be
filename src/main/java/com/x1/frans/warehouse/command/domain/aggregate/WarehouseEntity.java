package com.x1.frans.warehouse.command.domain.aggregate;

import com.x1.frans.user.command.aggregate.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouse")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private java.time.LocalDateTime createdAt;

    /** 창고 등록 */
    public static WarehouseEntity create(String code, String name, String address, UserEntity user) {
        return WarehouseEntity.builder()
                .code(code)
                .name(name)
                .address(address)
                .user(user)
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }
}