package com.x1.frans.supplier.command.application.service;

import com.x1.frans.supplier.command.vo.SupplierUpdateRequestVO;

public interface SupplierCommandService {

    void updateSupplier(long userId, long supplierId, SupplierUpdateRequestVO supplierUpdateRequestVO);

}
