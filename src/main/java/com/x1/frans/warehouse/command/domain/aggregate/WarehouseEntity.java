package com.x1.frans.warehouse.command.domain.aggregate;

import com.x1.frans.user.command.aggregate.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** 창고 등록 */
    public static WarehouseEntity create(String code, String name, String address, UserEntity user) {
        return WarehouseEntity.builder()
                .code(code)
                .name(name)
                .address(address)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /** 창고 수정 */
    public void updateInfo(String name, String address, UserEntity user) {
        this.name = name;
        this.address = address;
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }
}