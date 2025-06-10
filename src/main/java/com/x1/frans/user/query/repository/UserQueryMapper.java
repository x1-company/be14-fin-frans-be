package com.x1.frans.user.query.repository;

import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.query.dto.SearchFranchiseUserDTO;
import com.x1.frans.user.query.dto.SearchHqUserDTO;
import com.x1.frans.user.query.dto.SearchSupplierUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserQueryMapper {

    String findLatestUserCode(@Param("userType") String userType,
                              @Param("codePrefix") String codePrefix);

    boolean isEmailExist(@Param("email") String email);

    boolean isPhoneExist(@Param("phone") String phone);

    UserEntity findByCode(@Param("userCode") String userCode);

    List<SearchHqUserDTO> findHqUser(@Param("name") String name,
                                     @Param("departmentId") Long departmentId);

    List<SearchFranchiseUserDTO> findFranchiseUser(@Param("name") String name);

    List<SearchSupplierUserDTO> findSupplierUser(@Param("name") String name);
}
