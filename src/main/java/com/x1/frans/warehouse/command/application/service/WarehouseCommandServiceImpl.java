package com.x1.frans.warehouse.command.application.service;

import com.x1.frans.exception.InvalidDepartmentException;
import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.warehouse.command.application.service.dto.WarehouseCreateCommand;
import com.x1.frans.warehouse.command.domain.aggregate.WarehouseEntity;
import com.x1.frans.warehouse.command.domain.repository.WarehouseRepository;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseCommandServiceImpl implements WarehouseCommandService {

    private final WarehouseRepository warehouseRepository;
    private final UserCommandRepository userCommandRepository;

    @Transactional
    @Override
    public Long create(WarehouseCreateCommand command, Long userId, Long departmentId) {
        // 물류팀 권한 체크
        List<Long> allowedDeptIds = List.of(3L, 7L, 8L, 9L); // 물류팀, 물류1팀, 물류2팀, 물류3팀
        if (!allowedDeptIds.contains(departmentId)) {
            throw new InvalidDepartmentException("창고 등록은 '물류팀' 소속만 가능합니다.");
        }

        // 담당자(유저) 정보 조회
        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("담당자 정보 없음"));

        String newCode = generateNextWarehouseCode();

        WarehouseEntity entity = WarehouseEntity.builder()
                .code(newCode)
                .name(command.getName())
                .address(command.getAddress())
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        warehouseRepository.save(entity);
        return entity.getId();
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