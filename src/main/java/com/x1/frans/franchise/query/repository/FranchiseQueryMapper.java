package com.x1.frans.franchise.query.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FranchiseQueryMapper {
    String findLatestCodeByCodePrefix(String codePrefix);
}
