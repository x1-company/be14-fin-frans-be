package com.x1.frans.statistics.query.view.repository;

import com.x1.frans.statistics.query.view.dto.FranchiseReturnProductQueryDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface FranchiseReturnProductMapper {

    boolean existsFranchiseByManagerId(Long managerId);

    List<FranchiseReturnProductQueryDTO> selectStatsByManager(Long managerId, Integer year, Integer month);

    boolean existsFranchiseByDepartmentId(Long departmentId);

    List<FranchiseReturnProductQueryDTO> selectStatsByDepartment(Long deptId);

    List<FranchiseReturnProductQueryDTO> selectAllStats();

    String selectDutyNameById(Long dutyId);

    String selectDeptCodeById(Long departmentId);

    List<FranchiseReturnProductQueryDTO> selectStatsByManagerByFranchiseId(@Param("userId") Long userId,
                                                                         @Param("franchiseId") Long franchiseId);

}
