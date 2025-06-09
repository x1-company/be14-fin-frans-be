package com.x1.frans.supplier.command.application.service;

import com.x1.frans.exception.CustomException;
import com.x1.frans.exception.supplier.ErrorCode;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.supplier.command.domain.repository.SupplierCommandRepository;
import com.x1.frans.supplier.command.vo.SupplierUpdateRequestVO;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import com.x1.frans.user.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierCommandServiceImpl implements SupplierCommandService{

    private final SupplierCommandRepository supplierCommandRepository;
    private final UserCommandRepository userCommandRepository;

    @Autowired
    public SupplierCommandServiceImpl(SupplierCommandRepository supplierCommandRepository, UserCommandRepository userCommandRepository) {
        this.supplierCommandRepository = supplierCommandRepository;
        this.userCommandRepository = userCommandRepository;
    }

    @Transactional
    @Override
    public void updateSupplier(int userId, int supplierId, SupplierUpdateRequestVO supplierUpdateRequestVO) {

        // todo : 관리자 역할만 수정 가능
        // todo : 수정 예외처리

        SupplierEntity supplier = supplierCommandRepository.findById(supplierId)
                .orElseThrow(() -> new CustomException(ErrorCode.SUPPLIER_NOT_FOUND));

        UserEntity user = userCommandRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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
