package com.x1.frans.franchise.query.repository;

import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FranchiseQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);

    List<FranchiseListDTO> findAllFranchise();
}
