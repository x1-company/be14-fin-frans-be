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
        // code는 프론트에서 안 받고, 백엔드에서 자동 생성
        String nextCode = generateNextWarehouseCode();

        UserEntity user = UserCommandRepository.findById(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));

        WarehouseEntity warehouse = WarehouseEntity.create(
                nextCode,
                command.getName(),
                command.getAddress(),
                user
        );
        return warehouseRepository.save(warehouse).getId();
    }

    /** 창고코드 자동 생성 */
    private String generateNextWarehouseCode() {

        // DB에 WH-xxx 패턴이 없으면 기본값 WH-001
        String lastCode = warehouseRepository.findTopByOrderByIdDesc()
                .map(WarehouseEntity::getCode)
                .orElse(null);

        if (lastCode == null || !lastCode.matches("WH-\\d{3}")) {
            return "WH-001";
        }

        int lastNum = Integer.parseInt(lastCode.substring(3));
        int nextNum = lastNum + 1;
        return String.format("WH-%03d", nextNum); // WH-001, WH-002 ...
    }
}