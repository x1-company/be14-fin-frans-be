package com.x1.frans.user.command.controller;

import com.x1.frans.user.command.service.UserCommandService;
import com.x1.frans.user.command.vo.FranchiseUserRequestVO;
import com.x1.frans.user.command.vo.HqUserRequestVO;
import com.x1.frans.user.command.vo.SupplierUserRequestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hq/user")
@Tag(name = "🚶 회원", description = "본사 직원이 사용하는 회원 관련 기능들")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @Autowired
    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/hq")
    @Operation(
            summary = "본사 직원 등록",
            description = "본사 직원 정보를 받아 등록 후 메일 발송"
    )
    public ResponseEntity<Void> createHqUser(@RequestBody HqUserRequestVO hqUserRequestVO) {
        userCommandService.createHqUser(hqUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/franchise")
    @Operation(
            summary = "가맹점주 및 가맹점 등록",
            description = "가맹점주 및 가맹점 정보를 받아 등록 후 메일 발송"
    )
    public ResponseEntity<Void> createFranchiseUser(@RequestBody FranchiseUserRequestVO franchiseUserRequestVO) {
        userCommandService.createFranchiseUser(franchiseUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/supplier")
    @Operation(
            summary = "공급처 직원 및 공급처 등록",
            description = "공급처 직원 및 공급처 정보를 받아 등록 후 메일 발송"
    )
    public ResponseEntity<Void> createSupplierUser(@RequestBody SupplierUserRequestVO supplierUserRequestVO) {
        userCommandService.createSupplierUser(supplierUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
