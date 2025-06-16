package com.x1.frans.user.command.service;

import com.x1.frans.email.dto.UserCredentialsDTO;
import com.x1.frans.email.service.EmailService;
import com.x1.frans.exception.DuplicationException;
import com.x1.frans.exception.InvalidAddressException;
import com.x1.frans.exception.InvalidUserTypeException;
import com.x1.frans.franchise.command.domain.aggregate.FranchiseEntity;
import com.x1.frans.franchise.command.domain.repository.FranchiseCommandRepository;
import com.x1.frans.franchise.query.service.FranchiseQueryService;
import com.x1.frans.supplier.command.domain.aggregate.SupplierEntity;
import com.x1.frans.supplier.command.domain.repository.SupplierCommandRepository;
import com.x1.frans.supplier.query.service.SupplierQueryService;
import com.x1.frans.user.command.aggregate.HqUserDetailEntity;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.HqUserDetailCommandRepository;
import com.x1.frans.user.command.repository.UserCommandRepository;
import com.x1.frans.user.command.vo.CreateUserRequestVO;
import com.x1.frans.user.command.vo.FranchiseUserRequestVO;
import com.x1.frans.user.command.vo.HqUserRequestVO;
import com.x1.frans.user.command.vo.SupplierUserRequestVO;
import com.x1.frans.user.common.SeoulDistrictCode;
import com.x1.frans.user.enums.UserType;
import com.x1.frans.user.query.service.UserQueryService;
import com.x1.frans.user.util.GenerateRandomPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userCommandRepository;
    private final HqUserDetailCommandRepository hqUserDetailCommandRepository;
    private final FranchiseCommandRepository franchiseCommandRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserQueryService userQueryService;
    private final EmailService emailService;
    private final FranchiseQueryService franchiseQueryService;
    private final SupplierCommandRepository supplierCommandRepository;
    private final SupplierQueryService supplierQueryService;

    /**
     * 본사(HQ) 사용자 계정을 생성
     *
     * @param vo 본사 사용자 요청 정보
     */
    @Transactional
    @Override
    public void createHqUser(HqUserRequestVO vo) {

        validateUserCredentials(vo.getPhone(), vo.getEmail(), vo.getUserType());

        UserType userType = UserType.valueOf(vo.getUserType());
        UserCodeAndRawPw userCodeAndRawPw = generateUserCodeAndPassword(userType);
        UserEntity savedUser = createAndSaveUser(vo, userCodeAndRawPw);

        HqUserDetailEntity detail = buildHqUserDetail(savedUser, vo);
        hqUserDetailCommandRepository.save(detail);

        sendUserEmail(vo, userCodeAndRawPw);
    }

    /**
     * 가맹점 사용자 계정을 생성하고 가맹점 정보를 저장
     *
     * @param vo 가맹점 사용자 요청 정보
     */
    @Transactional
    @Override
    public void createFranchiseUser(FranchiseUserRequestVO vo) {

        validateUserCredentials(vo.getPhone(), vo.getEmail(), vo.getUserType());

        UserType userType = UserType.valueOf(vo.getUserType());
        UserCodeAndRawPw userCodeAndRawPw = generateUserCodeAndPassword(userType);
        UserEntity savedUser = createAndSaveUser(vo, userCodeAndRawPw);

        FranchiseEntity franchise = buildFranchise(savedUser, vo);
        franchiseCommandRepository.save(franchise);

        sendUserEmail(vo, userCodeAndRawPw);
    }

    /**
     * 공급처 사용자 계정을 생성하고 공급처 정보를 저장
     *
     * @param vo 공급처 사용자 요청 정보
     */
    @Transactional
    @Override
    public void createSupplierUser(SupplierUserRequestVO vo) {

        validateUserCredentials(vo.getPhone(), vo.getEmail(), vo.getUserType());

        UserType userType = UserType.valueOf(vo.getUserType());
        UserCodeAndRawPw userCodeAndRawPw = generateUserCodeAndPassword(userType);
        UserEntity savedUser = createAndSaveUser(vo, userCodeAndRawPw);

        SupplierEntity supplier = buildSupplier(savedUser, vo);
        supplierCommandRepository.save(supplier);

        sendUserEmail(vo, userCodeAndRawPw);
    }

    /**
     * 전화번호, 이메일, 사용자 타입이 유효한지 확인
     *
     * @param phone    사용자 전화번호
     * @param email    사용자 이메일
     * @param userType 사용자 타입 문자열 (ADMIN, HQ, FRANCHISE, SUPPLIER 중 하나)
     * @throws DuplicationException     전화번호 또는 이메일 중복 시
     * @throws InvalidUserTypeException 유효하지 않은 사용자 타입일 경우
     */
    private void validateUserCredentials(String phone, String email, String userType) {

        // 전화번호 중복 체크
        if (userQueryService.isPhoneExist(phone)) {
            throw new DuplicationException("이미 등록된 전화번호입니다.");
        }

        // 이메일 중복 체크
        if (userQueryService.isEmailExist(email)) {
            throw new DuplicationException("이미 등록된 이메일입니다.");
        }

        // userType 확인
        try {
            UserType.valueOf(userType);
        } catch (IllegalArgumentException e) {
            throw new InvalidUserTypeException("지원하지 않는 사용자 타입입니다: " + userType);
        }
    }

    /**
     * HqUserDetailEntity를 생성하여 값들을 설정
     *
     * @param savedUser 생성된 사용자
     * @param vo        본사 사용자 요청 VO
     * @return 설정된 HqUserDetailEntity
     */
    private HqUserDetailEntity buildHqUserDetail(UserEntity savedUser, HqUserRequestVO vo) {
        HqUserDetailEntity detail = new HqUserDetailEntity();
        detail.setUser(savedUser);
        detail.setDepartmentId(vo.getDepartmentId());
        detail.setPositionId(vo.getPositionId());
        detail.setDutyId(vo.getDutyId());
        return detail;
    }

    /**
     * FranchiseEntity를 생성하여 값들을 설정
     *
     * @param savedUser 가맹점 대표 사용자
     * @param vo        가맹점 요청 정보
     * @return 설정된 FranchiseEntity
     */
    private FranchiseEntity buildFranchise(UserEntity savedUser, FranchiseUserRequestVO vo) {
        FranchiseEntity franchise = new FranchiseEntity();
        franchise.setCode(createNewFranchiseCode(vo.getAddress(), vo.getSignedAt()));
        franchise.setName(vo.getFranchiseName());
        franchise.setAddress(vo.getAddress());
        franchise.setAddressDetail(vo.getAddressDetail());
        franchise.setZipcode(vo.getZipcode());
        franchise.setBusinessNumber(vo.getBusinessNumber());
        franchise.setPhone(vo.getFranchisePhone());
        franchise.setSignedAt(vo.getSignedAt());
        franchise.setDepartmentId(vo.getDepartmentId());
        franchise.setIsActive(true);
        franchise.setOwner(savedUser);
        franchise.setManagerId(vo.getManagerId());
        return franchise;
    }

    /**
     * SupplierEntity를 생성하여 값들을 설정
     *
     * @param savedUser 공급처 사용자
     * @param vo        공급처 요청 정보
     * @return 설정된 SupplierEntity
     */
    private SupplierEntity buildSupplier(UserEntity savedUser, SupplierUserRequestVO vo) {
        SupplierEntity supplier = new SupplierEntity();
        supplier.setCode(createNewSupplierCode(vo.getSignedAt()));
        supplier.setName(vo.getSupplierName());
        supplier.setCeoName(vo.getCeoName());
        supplier.setCompanyPhone(vo.getCompanyPhone());
        supplier.setAddress(vo.getAddress());
        supplier.setZipcode(vo.getZipcode());
        supplier.setBusinessNumber(vo.getBusinessNumber());
        supplier.setSignedAt(vo.getSignedAt());
        supplier.setIsActive(true);
        supplier.setCreatedAt(LocalDateTime.now());
        supplier.setUpdatedAt(LocalDateTime.now());
        supplier.setManagerId(vo.getManagerId());
        supplier.setSupplier(savedUser);

        return supplier;
    }

    private record UserCodeAndRawPw(String userCode, String rawPassword) {
    }

    /**
     * 사용자 코드와 랜덤 비밀번호를 생성
     *
     * @param userType 사용자 타입
     * @return 사용자 코드와 평문 비밀번호가 포함된 UserCodeAndRawPw
     */
    private UserCodeAndRawPw generateUserCodeAndPassword(UserType userType) {
        String userCode = createNewUserCode(userType);
        String rawPassword = GenerateRandomPassword.generate();
        return new UserCodeAndRawPw(userCode, rawPassword);
    }

    /**
     * 비밀번호 암호화 후 user 저장
     *
     * @param vo               사용자 생성 요청 데이터
     * @param userCodeAndRawPw 생성된 사용자 코드와 평문 비밀번호
     * @return UserEntity
     */
    private UserEntity createAndSaveUser(CreateUserRequestVO vo, UserCodeAndRawPw userCodeAndRawPw) {
        String encryptedPassword = bCryptPasswordEncoder.encode(userCodeAndRawPw.rawPassword());
        UserEntity user = vo.toUserEntity(userCodeAndRawPw.userCode(), encryptedPassword);
        return userCommandRepository.save(user);
    }

    /**
     * 사용자에게 계정 정보(아이디, 초기 비밀번호)를 이메일로 발송
     *
     * @param vo               사용자 요청 정보
     * @param userCodeAndRawPw 사용자 코드와 평문 비밀번호
     */
    private void sendUserEmail(CreateUserRequestVO vo, UserCodeAndRawPw userCodeAndRawPw) {
        UserCredentialsDTO dto = new UserCredentialsDTO();
        dto.setTo(vo.getEmail());
        dto.setName(vo.getName());
        dto.setUserCode(userCodeAndRawPw.userCode());
        dto.setRawPassword(userCodeAndRawPw.rawPassword());
        emailService.sendUserCredentials(dto, "SIGNUP");
    }

    /**
     * 공통 코드 생성 로직
     *
     * @param codePrefix 접미사
     * @param latestCode 가장 최근 코드
     * @return 새로운 코드
     */
    private String generateSequentialCode(String codePrefix, String latestCode) {
        int nextNumber = (latestCode == null)
                ? 1
                : Integer.parseInt(latestCode.substring(latestCode.length() - 4)) + 1;
        return codePrefix + String.format("%04d", nextNumber);
    }

    /**
     * 사용자 타입에 따라 새로운 userCode를 생성
     * 형식: [접두사][yyyyMM][4자리 일련번호] (예: HQ2025060001)
     *
     * @param userType 사용자 타입 (ADMIN, HQ, FRANCHISE, SUPPLIER)
     * @return 생성된 사용자 코드
     */
    private String createNewUserCode(UserType userType) {

        // prefix 설정
        String prefix = switch (userType) {
            case ADMIN -> "AD";
            case HQ -> "HQ";
            case FRANCHISE -> "FR";
            case SUPPLIER -> "SU";
        };

        // yyyyMM 포맷
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String codePrefix = prefix + yearMonth;

        // 해당 prefix + yearMonth를 포함하는 코드 검색
        String latestCode = userQueryService.findLatestUserCode(userType.name(), codePrefix);

        return generateSequentialCode(codePrefix, latestCode);
    }

    /**
     * 새로운 가맹점 코드 생성
     *
     * @param address  주소 정보
     * @param signedAt 계약 일자
     */
    private String createNewFranchiseCode(String address, LocalDate signedAt) {
        // 주소에서 서울의 25개 행정구 중 하나인지 확인
        String matchedDistrict = SeoulDistrictCode.CODES.keySet().stream()
                .filter(address::contains)
                .findFirst()
                .orElseThrow(() -> new InvalidAddressException("주소에 유효한 서울 행정구가 포함되어 있지 않습니다."));

        // 행정구에 맞게 코드 prefix
        String prefix = SeoulDistrictCode.CODES.get(matchedDistrict);

        // yyyyMM 포맷
        String yearMonth = signedAt.format(DateTimeFormatter.ofPattern("yyyyMM"));

        String codePrefix = prefix + yearMonth;

        // 해당 prefix + yearMonth를 포함하는 코드 검색
        String latestCode = franchiseQueryService.findLatestCodeByPrefixAndYearMonth(codePrefix);

        return generateSequentialCode(codePrefix, latestCode);
    }

    /**
     * 새로운 공급처 코드 생성
     *
     * @param signedAt 계약 일자
     */
    private String createNewSupplierCode(LocalDateTime signedAt) {

        String prefix = "SPG"; // supplier general

        // yyyyMM 포맷
        String yearMonth = signedAt.format(DateTimeFormatter.ofPattern("yyyyMM"));

        String codePrefix = prefix + yearMonth;

        // 해당 prefix + yearMonth를 포함하는 코드 검색
        String latestCode = supplierQueryService.findLatestCodeByCodePrefix(codePrefix);

        return generateSequentialCode(codePrefix, latestCode);
    }

    /**
     * 회원 계정 잠금(is_locked = true)
     *
     * @param userCode 회원 코드
     */
    @Transactional
    @Override
    public void accountLock(String userCode) {

        userCommandRepository.accountLock(userCode);
    }

    /**
     * 회원 서명 url 업데이트
     *
     * @param userId 회원 ID
     * @param signUrl S3 url
     */
    @Transactional
    @Override
    public void updateSignUrl(Long userId, String signUrl) {

        userCommandRepository.updateSignUrl(userId, signUrl);
    }
}
