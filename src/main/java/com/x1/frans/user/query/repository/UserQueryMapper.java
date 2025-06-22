package com.x1.frans.user.query.repository;

import com.x1.frans.user.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserQueryMapper {

    String findLatestUserCode(@Param("userType") String userType,
                              @Param("codePrefix") String codePrefix);

    boolean isEmailExist(@Param("email") String email);

    boolean isPhoneExist(@Param("phone") String phone);

    LoginUserDTO findByCode(@Param("userCode") String userCode);

    List<SearchHqUserDTO> findHqUser(@Param("name") String name,
                                     @Param("departmentIds") List<Long> departmentIds);

    List<SearchFranchiseUserDTO> findFranchiseUser(@Param("name") String name);

    List<SearchSupplierUserDTO> findSupplierUser(@Param("name") String name);

    HqUserDepartmentDTO getDepartmentInfo(@Param("userId") Long userId);

    List<Long> findFranchiseIdsByDepartmentId(Long departmentId);

    List<Long> findHqUserIdsByParentDepartmentIds(@Param("parentIds") List<Long> parentIds);
}
