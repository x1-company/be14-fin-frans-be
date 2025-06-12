package com.x1.frans.warehouse.command.application.service;

import com.x1.frans.exception.DepartmentNotFound;
import com.x1.frans.exception.InvalidDepartmentException;
import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.user.query.dto.HqUserDepartmentDTO;
import com.x1.frans.user.query.service.UserQueryService;
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
public class WarehouseCommandServiceImpl {

    private final WarehouseRepository warehouseRepository;
    private final UserCommandRepository userCommandRepository;
    private final UserQueryService userQueryService;

    @Transactional
    public Long create(WarehouseCreateCommand command) {
        UserEntity user = userCommandRepository.findById(command.getUserId())
                .orElseThrow(() -> new UserNotFoundException("담당자 정보 없음"));

        HqUserDepartmentDTO deptInfo = userQueryService.getDepartmentInfo(user.getId());
        if (deptInfo == null) {
            throw new DepartmentNotFound("부서 정보를 찾을 수 없습니다.");
        }
        Long deptId = deptInfo.getDepartmentId();
        List<Long> allowedDeptIds = List.of(3L, 7L, 8L, 9L); // 실제 id로

        if (!allowedDeptIds.contains(deptId)) {
            throw new InvalidDepartmentException("창고 등록은 '물류팀' 소속만 가능합니다.");
        }

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