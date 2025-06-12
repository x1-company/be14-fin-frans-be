package com.x1.frans.franchise.query.service;

import com.x1.frans.exception.FranchiseNotFoundException;
import com.x1.frans.exception.UnauthorizedAccessException;
import com.x1.frans.franchise.query.dto.FranchiseDetailDTO;
import com.x1.frans.franchise.query.dto.FranchiseListDTO;
import com.x1.frans.franchise.query.repository.FranchiseQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FranchiseQueryServiceImpl implements FranchiseQueryService {

    private final FranchiseQueryMapper franchiseQueryMapper;

    @Autowired
    public FranchiseQueryServiceImpl(FranchiseQueryMapper franchiseQueryMapper) {
        this.franchiseQueryMapper = franchiseQueryMapper;
    }

    @Override
    public String findLatestCodeByPrefixAndYearMonth(String codePrefix) {
        return franchiseQueryMapper.findLatestCodeByCodePrefix(codePrefix);
    }

    @Override
    public List<FranchiseListDTO> findFranchisesByDepartmentId(Long userId) {
        return franchiseQueryMapper.findFranchisesByDepartmentId(userId);
    }

    @Override
    public List<FranchiseListDTO> findFranchisesByManagerId(Long userId) {

        return franchiseQueryMapper.findFranchisesByManagerId(userId);
    }

    @Override
    public FranchiseDetailDTO findFranchiseDetailById(Long franchiseId, Long userId) {
        FranchiseDetailDTO results = franchiseQueryMapper.findFranchiseDetailById(franchiseId, userId);

        // 해당 가맹점에 접근 권한이 없거나 가맹점이 존재하지 않을때 예외처리
        if (results == null) {
            throw new UnauthorizedAccessException("해당 가맹점에 접근 권한이 없거나 존재하지 않습니다.");
        }

        return results;
    }
}
