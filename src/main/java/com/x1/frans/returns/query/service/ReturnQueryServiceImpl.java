package com.x1.frans.returns.query.service;

import com.x1.frans.returns.query.dto.*;
import com.x1.frans.returns.query.repository.ReturnQueryMapper;
import com.x1.frans.user.query.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnQueryServiceImpl implements ReturnQueryService {

    private final ReturnQueryMapper returnQueryMapper;
    private final UserQueryService userQueryService;

    @Autowired
    public ReturnQueryServiceImpl(ReturnQueryMapper returnQueryMapper, UserQueryService userQueryService) {
        this.returnQueryMapper = returnQueryMapper;
        this.userQueryService = userQueryService;
    }

    @Override
    public List<ShippedOrderDTO> findDeliveredOrdersWithinLastWeek(Long userId, Long franchiseId) {
        return returnQueryMapper.findDeliveredOrdersWithinLastWeek(userId, franchiseId);
    }

    @Override
    public List<ProductOrderDTO> findProductListByOrderId(Long userId, Long franchiseId, Long orderId) {
        return returnQueryMapper.findProductListByOrderId(userId, franchiseId, orderId);
    }

    @Override
    public ReturnSearchPageDTO findAllReturns(Long userId, ReturnSearchConditionDTO condition) {

        List<Long> franchiseIds = returnQueryMapper.findFranchiseIdsByUserId(userId);
        condition.setFranchiseIds(franchiseIds);

        // franchiseId가 null이면 HQ 유저, null 아니면 프랜차이즈 유저
        if (condition.getFranchiseIds() == null || condition.getFranchiseIds().isEmpty()) {
            List<Long> allowedFranchiseIds = userQueryService.getAccessibleFranchiseIdsForUser(userId);
            condition.setFranchiseIds(allowedFranchiseIds);
        }

        // 페이지 오프셋 계산
        int offset = (condition.getPage() - 1) * condition.getSize();
        condition.setOffset(offset);

        // 주문 목록 및 총 개수 조회
        List<ReturnListDTO> list = returnQueryMapper.searchReturns(condition);
        int totalCount = returnQueryMapper.countReturns(condition);
        int totalPages = (int) Math.ceil((double) totalCount / condition.getSize());

        return ReturnSearchPageDTO.builder()
                .list(list)
                .totalCount(totalCount)
                .page(condition.getPage())
                .size(condition.getSize())
                .totalPages(totalPages)
                .hasNext(condition.getPage() < totalPages)
                .hasPrevious(condition.getPage() > 1)
                .build();
    }
}
