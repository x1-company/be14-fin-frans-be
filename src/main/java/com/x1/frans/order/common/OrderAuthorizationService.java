package com.x1.frans.order.common;

import com.x1.frans.exception.OrderNotFoundException;
import com.x1.frans.exception.UnauthorizedAccessException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.repository.OrderCommandRepository;
import com.x1.frans.user.query.dto.HqUserDepartmentDTO;
import com.x1.frans.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAuthorizationService {

    private final OrderCommandRepository orderCommandRepository;
    private final UserQueryService userQueryService;

    public Order getAuthorizedOrder(Long orderId, Long userId) {
        Order order = orderCommandRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다."));

        FranchiseEntity franchise = order.getFranchise();
        if (franchise == null) {
            throw new UnauthorizedAccessException("주문에 가맹점 정보가 없습니다.");
        }

        HqUserDepartmentDTO dept = userQueryService.getDepartmentInfo(userId);

        // 본사 직원(HQ)인 경우
        if (dept != null && dept.getDepartmentId() != null) {
            if (!dept.getDepartmentId().equals(franchise.getDepartmentId())) {
                throw new UnauthorizedAccessException("해당 가맹점에 접근 권한이 없습니다.");
            }
        }
        // 가맹점 유저(FRANCHISE)인 경우
        else {
            if (!order.getUser().getId().equals(userId)) {
                throw new UnauthorizedAccessException("본인이 소유한 주문이 아닙니다.");
            }
        }

        return order;
    }
}
