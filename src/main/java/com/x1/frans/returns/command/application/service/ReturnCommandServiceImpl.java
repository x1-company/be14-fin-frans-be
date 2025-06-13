package com.x1.frans.returns.command.application.service;

import com.x1.frans.exception.FranchiseNotFoundException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.returns.command.domain.aggregate.ReturnEntity;
import com.x1.frans.returns.command.domain.repository.ReturnCommandRepository;
import com.x1.frans.returns.command.domain.vo.RegisterReturnRequestVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReturnCommandServiceImpl implements ReturnCommandService {

    private final ReturnCommandRepository returnCommandRepository;

    public ReturnCommandServiceImpl(ReturnCommandRepository returnCommandRepository) {
        this.returnCommandRepository = returnCommandRepository;
    }

    @Override
    @Transactional
    public void registReturn(Long franchiseId, RegisterReturnRequestVO vo, Long userId) {

    }
}
