package com.x1.frans.warehouse.command.application.service;

import com.x1.frans.warehouse.command.application.service.dto.WarehouseCreateCommand;
import com.x1.frans.warehouse.command.domain.aggregate.WarehouseEntity;
import com.x1.frans.warehouse.command.domain.repository.WarehouseRepository;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseCommandService {

    private final WarehouseRepository warehouseRepository;
    private final UserCommandRepository UserCommandRepository;

    @Transactional
    public Long create(WarehouseCreateCommand command) {
        if (warehouseRepository.existsByCode(command.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 창고 코드입니다.");
        }

        UserEntity user = UserCommandRepository.findById(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));

        WarehouseEntity warehouse = WarehouseEntity.create(
                command.getCode(),
                command.getName(),
                command.getAddress(),
                user
        );
        return warehouseRepository.save(warehouse).getId();
    }
}