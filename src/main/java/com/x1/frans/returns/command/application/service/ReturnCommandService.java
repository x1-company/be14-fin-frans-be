package com.x1.frans.returns.command.application.service;

import com.x1.frans.returns.command.domain.vo.*;

public interface ReturnCommandService {

    void registReturn(Long franchiseId, ReturnCreateRequestVO vo, Long userId);

    void completeReview(Long returnId, ReturnReviewCompleteRequestVO vo, Long userId);

    void reject(Long returnId, ReturnRejectRequestVO vo, Long userId);

    void updateDeliveryInfo(Long returnId, ReturnDeliveryInfoRequestVO vo, Long userId);

    void updateDeliveredAt(Long returnId, ReturnDeliveredAtVO vo, Long userId);

    void returnComplete(Long returnId, Long userId);
}
