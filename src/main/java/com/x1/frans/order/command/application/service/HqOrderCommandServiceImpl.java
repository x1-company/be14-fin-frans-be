package com.x1.frans.order.command.application.service;

import com.x1.frans.exception.DuplicateDeadlineTimeException;
import com.x1.frans.exception.OrderNotFoundException;
import com.x1.frans.exception.UnauthorizedAccessException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.order.command.domain.aggregate.Order;
import com.x1.frans.order.command.domain.aggregate.StoreOrderDeadline;
import com.x1.frans.order.command.domain.repository.OrderCommandRepository;
import com.x1.frans.order.command.domain.repository.StoreOrderDeadlineRepository;
import com.x1.frans.user.query.dto.HqUserDepartmentDTO;
import com.x1.frans.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class HqOrderCommandServiceImpl implements HqOrderCommandService {

    private final StoreOrderDeadlineRepository storeOrderDeadlineRepository;
    private final OrderCommandRepository orderCommandRepository;
    private final UserQueryService userQueryService;

    @Override
    @Transactional
    public boolean createOrUpdateDeadline(LocalTime deadlineTime) {
        StoreOrderDeadline deadline = storeOrderDeadlineRepository.findById(1L).orElse(null);

        if (deadline == null) {
            StoreOrderDeadline newDeadline = new StoreOrderDeadline(deadlineTime);
            storeOrderDeadlineRepository.save(newDeadline);
            return true;
        } else {
            if (deadline.getOrderDeadlineAt().equals(deadlineTime)) {
                throw new DuplicateDeadlineTimeException("기존과 동일한 주문 마감 시간입니다.");
            }
            deadline.updateDeadlineTime(deadlineTime);
            return false;
        }
    }

    @Override
    @Transactional
    public void rejectOrder(Long orderId, String reason, Long userId) {
        Order order = getAuthorizedOrder(orderId, userId);
        order.reject(reason);
    }

    @Override
    @Transactional
    public void markReviewComplete(Long orderId, Long userId) {
        Order order = getAuthorizedOrder(orderId, userId);
        order.markReviewComplete();
    }

    private Order getAuthorizedOrder(Long orderId, Long userId) {
        Order order = orderCommandRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다."));

        FranchiseEntity franchise = order.getFranchise();

        // 본인 부서 조회
        HqUserDepartmentDTO dept = userQueryService.getDepartmentInfo(userId);

        if (dept == null || dept.getDepartmentId() == null) {
            throw new UnauthorizedAccessException("부서 정보가 없어서 접근 권한이 없습니다");
        }

        //  부서 소속 가맹점인지 검증
        if (!dept.getDepartmentId().equals(franchise.getDepartmentId())) {
            throw new UnauthorizedAccessException("해당 가맹점에 접근 권한이 없습니다");
        }

        return order;
    }

}
