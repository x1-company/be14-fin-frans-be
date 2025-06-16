package com.x1.frans.order.query.service.support;

import com.x1.frans.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FranchiseAccessSupport {
    private final UserQueryService userQueryService;

    public List<Long> getAccessibleFranchisesOrThrow(Long userId, List<Long> FranchiseIds) {
        if (FranchiseIds == null || FranchiseIds.isEmpty()) {
            return userQueryService.getAccessibleFranchiseIdsForUser(userId);
        }
        return FranchiseIds;
    }
}
