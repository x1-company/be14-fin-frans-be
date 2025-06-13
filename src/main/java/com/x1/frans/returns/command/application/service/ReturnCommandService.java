package com.x1.frans.returns.command.application.service;

import com.x1.frans.returns.command.domain.vo.RegisterReturnRequestVO;

public interface ReturnCommandService {

    void registReturn(Long franchiseId, RegisterReturnRequestVO vo, Long userId);

}
