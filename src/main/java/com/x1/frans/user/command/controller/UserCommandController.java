package com.x1.frans.user.command.controller;

import com.x1.frans.auth.command.application.service.AuthCommandService;
import com.x1.frans.exception.AccessDeniedException;
import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.user.command.service.UserCommandService;
import com.x1.frans.user.command.vo.FranchiseUserRequestVO;
import com.x1.frans.user.command.vo.HqUserRequestVO;
import com.x1.frans.user.command.vo.UpdateSignUrlRequestVO;
import com.x1.frans.user.command.vo.SupplierUserRequestVO;
import com.x1.frans.user.enums.DepartmentType;
import com.x1.frans.user.enums.PositionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/hq/user")
@Tag(name = "🚶 회원", description = "본사 직원이 사용하는 회원 관련 기능들")
public class UserCommandController {

    private final UserCommandService userCommandService;
    private final AuthCommandService authCommandService;

    @Autowired
    public UserCommandController(UserCommandService userCommandService,
                                 AuthCommandService authCommandService) {
        this.userCommandService = userCommandService;
        this.authCommandService = authCommandService;
    }

    @PostMapping("/hq")
    @Operation(
            summary = "본사 직원 등록",
            description = "본사 직원 정보를 받아 등록 후 메일 발송. 인사팀 혹은 부장 직급만 가능"
    )
    public ResponseEntity<Void> createHqUser(@RequestBody HqUserRequestVO hqUserRequestVO,
                                             @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        validateDPTIsHRM(customUserDetails.getDepartmentId(), customUserDetails.getPositionId());

        userCommandService.createHqUser(hqUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/franchise")
    @Operation(
            summary = "가맹점주 및 가맹점 등록",
            description = "가맹점주 및 가맹점 정보를 받아 등록 후 메일 발송. 인사팀 혹은 부장 직급만 가능"
    )
    public ResponseEntity<Void> createFranchiseUser(@RequestBody FranchiseUserRequestVO franchiseUserRequestVO,
                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        validateDPTIsHRM(customUserDetails.getDepartmentId(), customUserDetails.getPositionId());

        userCommandService.createFranchiseUser(franchiseUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/supplier")
    @Operation(
            summary = "공급처 직원 및 공급처 등록",
            description = "공급처 직원 및 공급처 정보를 받아 등록 후 메일 발송. 인사팀 혹은 부장 직급만 가능"
    )
    public ResponseEntity<Void> createSupplierUser(@RequestBody SupplierUserRequestVO supplierUserRequestVO,
                                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        validateDPTIsHRM(customUserDetails.getDepartmentId(), customUserDetails.getPositionId());

        userCommandService.createSupplierUser(supplierUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/sign")
    @Operation(
            summary = "서명 url 변경",
            description = "서명 url 변경"
    )
    public ResponseEntity<Void> updateSignUrl(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                              @RequestBody UpdateSignUrlRequestVO updateSignUrlRequestVO,
                                              HttpServletRequest request, HttpServletResponse response) {

        userCommandService.updateSignUrl(customUserDetails.getUserId(),
                updateSignUrlRequestVO.getSignUrl(),
                customUserDetails.getSignUrl());

        String refreshToken = authCommandService.extractRefreshTokenFromCookie(request);

        String newAccessToken = authCommandService.reissueAccessToken(refreshToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        return ResponseEntity.ok().build();
    }

    private void validateDPTIsHRM(Long departmentId, Long positionId) {

       if (!Objects.equals(departmentId, DepartmentType.HRM.getId()) &&
               !Objects.equals(PositionType.GM.getId(), positionId)
       ) {
           throw new AccessDeniedException("해당 기능에 대한 권한이 없습니다!");
       }
    }
}
