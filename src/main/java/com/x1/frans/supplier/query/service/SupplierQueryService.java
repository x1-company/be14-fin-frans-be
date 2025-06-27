package com.x1.frans.supplier.query.service;

import com.x1.frans.supplier.query.dto.MyInfoDTO;

public interface SupplierQueryService {
    MyInfoDTO getMyInfo(Long userId);
}
