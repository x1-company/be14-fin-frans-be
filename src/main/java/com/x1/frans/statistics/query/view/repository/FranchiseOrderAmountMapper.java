package com.x1.frans.statistics.query.view.repository;

import com.x1.frans.statistics.query.view.dto.FranchiseOrderAmountQueryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FranchiseOrderAmountMapper {

    boolean existsFranchiseByManagerId(Long managerId);

    List<FranchiseOrderAmountQueryDTO> selectStatsByManager(Long managerId, Integer year, Integer month);

    boolean existsFranchiseByDepartmentId(Long departmentId);

    List<FranchiseOrderAmountQueryDTO> selectStatsByDepartment(Long departmentId);

    List<FranchiseOrderAmountQueryDTO> selectAllStats();

    String selectDutyNameById(Long dutyId);

    String selectDeptCodeById(Long departmentId);

    List<FranchiseOrderAmountQueryDTO> selectStatsByManagerByFranchiseId(@Param("userId") Long userId,
                                                                         @Param("franchiseId") Long franchiseId);
}
