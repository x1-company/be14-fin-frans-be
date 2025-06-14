package com.x1.frans.returns.command.application.service;

import com.x1.frans.returns.command.domain.vo.ReturnCreateRequestVO;

public interface ReturnCommandService {

    void registReturn(Long franchiseId, ReturnCreateRequestVO vo, Long userId);

}
