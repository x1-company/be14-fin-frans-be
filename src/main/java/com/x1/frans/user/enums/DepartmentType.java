package com.x1.frans.user.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public enum DepartmentType {

    PUR(1, "구매팀", 1, null),
    SAL(2, "영업팀", 1, null),
    LOG(3, "물류팀", 1, null),
    SAL_1(4, "영업1팀", 2, 2L),
    SAL_2(5, "영업1팀", 2, 2L),
    SAL_3(6, "영업1팀", 2, 2L),
    LOG_1(7, "물류1팀", 2, 3L),
    LOG_2(8, "물류2팀", 2, 3L),
    LOG_3(9, "물류3팀", 2, 3L),
    HRM(10, "인사팀", 1, null);

    private final long id;
    private final String label;
    private final int depth;
    private final Long parentId;

    DepartmentType(long id, String label,
                   int depth, Long parentId) {
        this.id = id;
        this.label = label;
        this.depth = depth;
        this.parentId = parentId;
    }

    /**
     * 특정 ID 기준으로 모든 하위 부서 ID 포함한 리스트 반환
     */
    public static List<Long> getSelfAndAllSubDepartmentIds(Long rootId) {
        List<Long> result = new ArrayList<>();
        result.add(rootId);
        collectChildren(rootId, result);
        return result;
    }

    private static void collectChildren(Long parentId, List<Long> result) {
        for (DepartmentType dept : DepartmentType.values()) {
            if (Objects.equals(dept.getParentId(), parentId)) {
                result.add(dept.getId());
                collectChildren(dept.getId(), result); // 재귀적으로 하위도 탐색
            }
        }
    }
}
