package com.x1.frans.user.command.service;

import com.x1.frans.email.dto.UserCredentialsDTO;
import com.x1.frans.email.service.EmailService;
import com.x1.frans.exception.DuplicationException;
import com.x1.frans.exception.InvalidUserTypeException;
import com.x1.frans.user.command.aggregate.HqUserDetailEntity;
import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.command.repository.HqUserDetailCommandRepository;
import com.x1.frans.user.command.repository.UserCommandRepository;
import com.x1.frans.user.command.vo.HqUserRequestVO;
import com.x1.frans.user.enums.UserType;
import com.x1.frans.user.query.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userCommandRepository;
    private final HqUserDetailCommandRepository hqUserDetailCommandRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserQueryService userQueryService;
    private final EmailService emailService;

    @Autowired
    public UserCommandServiceImpl(UserCommandRepository userRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  HqUserDetailCommandRepository hqUserDetailRepository,
                                  EmailService emailService,
                                  UserQueryService userQueryService) {
        this.userCommandRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hqUserDetailCommandRepository = hqUserDetailRepository;
        this.emailService = emailService;
        this.userQueryService = userQueryService;
    }

    /**
     * userType에 맞게 새로운 userCode 생성
     * @param userType enum
     * @return String newUserCode
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

        // 가장 최근 코드 조회
        String latestUserCode = userQueryService.findLatestUserCode(userType.name(), codePrefix);
        if (latestUserCode == null) {
            return codePrefix + "0001";
        } else {
            String last4Digits = latestUserCode.substring(latestUserCode.length() - 4);
            int nextNumber = Integer.parseInt(last4Digits) + 1;
            return codePrefix + String.format("%04d", nextNumber);
        }
    }

    private static final String CHAR_POOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int PASSWORD_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 초기 랜덤 비밀번호 6자리 생성
     * @return String randomPassword
     */
    private String generateRandomPassword() {
        StringBuilder randomPassword = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            randomPassword.append(CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length())));
        }

        return randomPassword.toString();
    }

    /**
     * 유효한 전화번호, 이메일, 사용자 타입인지 확인
     * @param phone 전화번호
     * @param email 이메일
     * @param userType 사용자 타입(ADMIN, HQ, FRANCHISE, SUPPLIER)
     */
    private void checkUserCredentials(String phone, String email, String userType) {

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
     * UserEntity 인스턴스 생성 후 값 할당
     * @param vo 요청 시 body 정보
     * @param userCode 새로 생성된 회원 코드
     * @param encryptedPassword 암호화된 비밀번호
     * @return UserEntity user
     */
    private UserEntity buildUser(HqUserRequestVO vo, String userCode, String encryptedPassword) {
        UserEntity user = new UserEntity();
        user.setCode(userCode);
        user.setPassword(encryptedPassword);
        user.setName(vo.getName());
        user.setPhone(vo.getPhone());
        user.setEmail(vo.getEmail());
        user.setType(UserType.valueOf(vo.getUserType()));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsTempPassword(true);
        user.setProfileUrl(vo.getProfileUrl());
        return user;
    }

    /**
     * HqUserDetailEntity 인스턴스 생성 후 값 할당
     * @param savedUser 새로 생성될 user
     * @param vo 요청 시 body 정보
     * @return HqUserDetailEntity detail
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
     * 이메일 발송에 필요한 인스턴스 생성 후 값 할당
     * @param vo 요청 시 body 정보
     * @param userCode 새로 생성된 회원 코드
     * @param rawPassword 암호화되지 않은 비밀번호
     * @return UserCredentialsDTO dto
     */
    private UserCredentialsDTO buildUserCredentials(HqUserRequestVO vo, String userCode, String rawPassword) {
        UserCredentialsDTO dto = new UserCredentialsDTO();
        dto.setTo(vo.getEmail());
        dto.setName(vo.getName());
        dto.setUserCode(userCode);
        dto.setRawPassword(rawPassword);
        return dto;
    }

    /**
     * 본사 직원 계정 생성
     * @param hqUserRequestVO 본사 계정 생성 시 필요한 정보
     */
    @Transactional
    @Override
    public void createHqUser(HqUserRequestVO hqUserRequestVO) {

        checkUserCredentials(hqUserRequestVO.getPhone(),
                                hqUserRequestVO.getEmail(),
                                hqUserRequestVO.getUserType());

        UserType userTypeEnum = UserType.valueOf(hqUserRequestVO.getUserType());

        // 새로운 userCode 생성
        String userCode = createNewUserCode(userTypeEnum);

        // 랜덤 비밀번호 생성
        String randomPw = generateRandomPassword();

        // 생성된 비밀번호 암호화
        String encryptedPassword = bCryptPasswordEncoder.encode(randomPw);

        // user 정보 저장
        UserEntity user = buildUser(hqUserRequestVO, userCode, encryptedPassword);
        UserEntity savedUser = userCommandRepository.save(user);
        
        // hqUserDetail 정보 저장
        HqUserDetailEntity detail = buildHqUserDetail(savedUser, hqUserRequestVO);
        hqUserDetailCommandRepository.save(detail);

        // 유저에게 메일 발송
        UserCredentialsDTO dto = buildUserCredentials(hqUserRequestVO, userCode, randomPw);
        emailService.sendUserCredentials(dto);
    }
}
