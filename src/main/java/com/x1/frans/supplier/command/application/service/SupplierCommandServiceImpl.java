package com.x1.frans.supplier.command.application.service;

import com.x1.frans.exception.CustomException;
import com.x1.frans.exception.supplier.ErrorCode;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.supplier.command.domain.repository.SupplierCommandRepository;
import com.x1.frans.supplier.command.vo.SupplierUpdateRequestVO;
import com.x1.frans.user.command.repository.UserCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierCommandServiceImpl implements SupplierCommandService{

    private final SupplierCommandRepository supplierCommandRepository;

    @Autowired
    public SupplierCommandServiceImpl(SupplierCommandRepository supplierCommandRepository) {
        this.supplierCommandRepository = supplierCommandRepository;
    }

    @Transactional
    @Override
    public void updateSupplier(long userId, long supplierId, SupplierUpdateRequestVO supplierUpdateRequestVO) {

        SupplierEntity supplier = supplierCommandRepository.findById(supplierId)
                .orElseThrow(() -> new CustomException(ErrorCode.SUPPLIER_NOT_FOUND));

        if (!supplier.getManagerId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        supplier.setName(supplierUpdateRequestVO.getName());
        supplier.setCeoName(supplierUpdateRequestVO.getCeoName());
        supplier.setCompanyPhone(supplierUpdateRequestVO.getCompanyPhone());
        supplier.setAddress(supplierUpdateRequestVO.getAddress());
        supplier.setZipcode(supplierUpdateRequestVO.getZipcode());
        supplier.setBusinessNumber(supplierUpdateRequestVO.getBusinessNumber());
        supplier.setSignedAt(supplierUpdateRequestVO.getSignedAt());
        supplier.setIsActive(supplierUpdateRequestVO.getIsActive());


    }

}
