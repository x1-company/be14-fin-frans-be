package com.x1.frans.franchise.command.application.service;

import com.x1.frans.exception.FranchiseNotFoundException;
import com.x1.frans.exception.InvalidFranchiseArgumentException;
import com.x1.frans.exception.UnauthorizedAccessException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.franchise.command.domain.repository.FranchiseCommandRepository;
import com.x1.frans.franchise.command.domain.vo.UpdateFranchiseRequestVO;
import com.x1.frans.user.query.dto.HqUserDepartmentDTO;
import com.x1.frans.user.query.service.UserQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FranchiseCommandServiceImpl implements FranchiseCommandService {

    private final FranchiseCommandRepository franchiseCommandRepository;
    private final UserQueryService userQueryService;

    public FranchiseCommandServiceImpl(FranchiseCommandRepository franchiseCommandRepository, UserQueryService userQueryService) {
        this.franchiseCommandRepository = franchiseCommandRepository;
        this.userQueryService = userQueryService;
    }

    /**
     * 가맹점 정보 업데이트
     *
     * @param franchiseId 가맹점 ID
     * @param vo 가맹점 수정 요청 정보
     * @param userId 영업팀 관리자 ID
     */
    @Override
    @Transactional
    public void updateFranchiseInfo(int franchiseId, UpdateFranchiseRequestVO vo, long userId) {
        FranchiseEntity franchise = franchiseCommandRepository.findById(franchiseId)
                .orElseThrow(() -> new FranchiseNotFoundException("가맹점을 찾을 수 없습니다"));

        // 유효성 검사 수행
        validateFranchiseUpdateRequest(vo);

        // 본인이 속한 부서의 가맹점 정보에만 접근 권한 부여
        HqUserDepartmentDTO dept = userQueryService.getDepartmentInfo(userId);
        if (!(dept.getDepartmentId().equals(franchise.getDepartmentId()))) {
            throw new UnauthorizedAccessException("해당 가맹점에 접근 권한이 없습니다");
        }

        franchise.setName(vo.getName());
        franchise.setAddress(vo.getAddress());
        franchise.setAddressDetail(vo.getAddressDetail());
        franchise.setZipcode(vo.getZipcode());
        franchise.setBusinessNumber(vo.getBusinessNumber());
        franchise.setPhone(vo.getPhone());
        franchise.setSignedAt(vo.getSignedAt());
        franchise.setIsActive(vo.getIsActive());
    }

    /**
     * 가맹점 정보 업데이트 시 필수 값이 모두 입력되었는지 검증
     *
     * @param vo UpdateFranchiseRequestVO 객체
     * @throws IllegalArgumentException 필수 필드가 누락된 경우
     */
    private void validateFranchiseUpdateRequest(UpdateFranchiseRequestVO vo) {

        // 이름 누락 확인
        if (vo.getName() == null || vo.getName().trim().isEmpty()) {
            throw new InvalidFranchiseArgumentException("가맹점 이름은 필수 입력값입니다.");
        }

        // 주소 누락 확인
        if (vo.getAddress() == null || vo.getAddress().trim().isEmpty()) {
            throw new InvalidFranchiseArgumentException("주소는 필수 입력값입니다.");
        }

        // 우편번호 누락 확인
        if (vo.getZipcode() == null || vo.getZipcode().trim().isEmpty()) {
            throw new InvalidFranchiseArgumentException("우편번호는 필수 입력값입니다.");
        }

        // 사업자등록번호 누락 확인
        if (vo.getBusinessNumber() == null || vo.getBusinessNumber().trim().isEmpty()) {
            throw new InvalidFranchiseArgumentException("사업자등록번호는 필수 입력값입니다.");
        }

        // 전화번호 누락 확인
        if (vo.getPhone() == null || vo.getPhone().trim().isEmpty()) {
            throw new InvalidFranchiseArgumentException("전화번호는 필수 입력값입니다.");
        }

        // 계약일자 누락 확인
        if (vo.getSignedAt() == null) {
            throw new InvalidFranchiseArgumentException("계약일자는 필수 입력값입니다.");
        }

        // 활성화 여부 누락 확인
        if (vo.getIsActive() == null) {
            throw new InvalidFranchiseArgumentException("활성화 여부는 필수 입력값입니다.");
        }
    }
}
