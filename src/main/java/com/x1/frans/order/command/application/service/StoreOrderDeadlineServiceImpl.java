package com.x1.frans.order.command.application.service;

import com.x1.frans.order.command.domain.aggregate.StoreOrderDeadline;
import com.x1.frans.order.command.domain.repository.StoreOrderDeadlineRepository;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreOrderDeadlineServiceImpl implements StoreOrderDeadlineService {

    private final StoreOrderDeadlineRepository storeOrderDeadlineRepository;

    @Override
    @Transactional
    public boolean createOrUpdateDeadline(LocalTime deadlineTime) {
        StoreOrderDeadline deadline = storeOrderDeadlineRepository.findById(1).orElse(null);

        if (deadline == null) {
            StoreOrderDeadline newDeadline = new StoreOrderDeadline(deadlineTime);
            storeOrderDeadlineRepository.save(newDeadline);
            return true;
        } else {
            deadline.updateDeadlineTime(deadlineTime);
            return false;
        }
    }
}
