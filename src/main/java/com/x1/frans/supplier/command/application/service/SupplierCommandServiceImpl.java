package com.x1.frans.supplier.command.application.service;

import com.x1.frans.exception.InvalidSupplierArgumentException;
import com.x1.frans.exception.SupplierNotFoundException;
import com.x1.frans.exception.UnauthorizedAccessException;
import com.x1.frans.exception.UserNotFoundException;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.supplier.command.domain.repository.SupplierCommandRepository;
import com.x1.frans.supplier.command.vo.SupplierUpdateRequestVO;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.UserCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public void updateSupplier(long userId, long supplierId, SupplierUpdateRequestVO vo) {

        SupplierEntity supplier = supplierCommandRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("공급처를 찾을 수 없습니다."));
//        UserEntity manager = userCommandRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        UserEntity manager = supplier.getUser();
//        if (!manager.getId().equals(userId)) {
//                throw new UnauthorizedAccessException("접근 권한이 없습니다.");
//            }
        // 유효성 검사 수행
        validateSupplierUpdateRequest(vo);

        supplier.setName(vo.getName());
        supplier.setCeoName(vo.getCeoName());
        supplier.setCompanyPhone(vo.getCompanyPhone());
        supplier.setAddress(vo.getAddress());
        supplier.setZipcode(vo.getZipcode());
        supplier.setBusinessNumber(vo.getBusinessNumber());
        supplier.setSignedAt(vo.getSignedAt());
        supplier.setIsActive(vo.getIsActive());
        supplier.setUpdatedAt(LocalDateTime.now());
        manager.setName(vo.getSupplierName());
        manager.setEmail(vo.getSupplierEmail());
        manager.setPhone(vo.getSupplierPhone());




    }

    /**
     * 공급처 정보 업데이트 시 필수 값이 모두 입력되었는지 검증
     *
     * @param vo SupplierUpdateRequestVO 객체
     * @throws IllegalArgumentException 필수 필드가 누락된 경우
     */
    private void validateSupplierUpdateRequest(SupplierUpdateRequestVO vo) {

        // 공급처명
        if (vo.getName() == null || vo.getName().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("공급처명은 필수 입력값입니다.");
        }

        // 대표자명
        if (vo.getCeoName() == null || vo.getCeoName().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("대표자명은 필수 입력값입니다.");
        }

        // 대표자 번호
        if (vo.getCompanyPhone() == null || vo.getCompanyPhone().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("대표자 번호는 필수 입력값입니다.");
        }

        // 주소
        if (vo.getAddress() == null || vo.getAddress().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("주소는 필수 입력값입니다.");
        }

        // 우편번호
        if (vo.getZipcode() == null || vo.getZipcode().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("우편번호는 필수 입력값입니다.");
        }

        // 사업자등록번호
        if (vo.getBusinessNumber() == null || vo.getBusinessNumber().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("사업자등록번호는 필수 입력값입니다.");
        }

        // 계약일자
        if (vo.getSignedAt() == null) {
            throw new InvalidSupplierArgumentException("계약일자는 필수 입력값입니다.");
        }

        // 거래여부
        if (vo.getIsActive() == null) {
            throw new InvalidSupplierArgumentException("거래여부는 필수 입력값입니다.");
        }

        // 공급처 담당자명
        if (vo.getSupplierName() == null || vo.getSupplierName().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("공급처 담당자명은 필수 입력값입니다.");
        }

        // 공급처 담당자 이메일
        if (vo.getSupplierEmail() == null || vo.getSupplierEmail().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("공급처 담당자 이메일은 필수 입력값입니다.");
        }

        // 공급처 담당자 전화번호
        if (vo.getSupplierPhone() == null || vo.getSupplierPhone().trim().isEmpty()) {
            throw new InvalidSupplierArgumentException("공급처 담당자 전화번호는 필수 입력값입니다.");
        }

    }

}


