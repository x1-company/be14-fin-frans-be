package com.x1.frans.returns.command.application.service;

import com.x1.frans.returns.command.domain.vo.ReturnCreateRequestVO;
import com.x1.frans.returns.command.domain.vo.ReturnRejectRequestVO;
import com.x1.frans.returns.command.domain.vo.ReturnReviewCompleteRequestVO;

public interface ReturnCommandService {

    void registReturn(Long franchiseId, ReturnCreateRequestVO vo, Long userId);

    void completeReview(Long returnId, ReturnReviewCompleteRequestVO vo, Long userId);

    void reject(Long returnId, ReturnRejectRequestVO vo, Long userId);
}
