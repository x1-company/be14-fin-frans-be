package com.x1.frans.user.query.controller;

import com.x1.frans.security.CustomUserDetails;
import com.x1.frans.user.query.dto.*;
import com.x1.frans.user.query.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hq/user")
@Tag(name = "🚶 회원", description = "본사 직원이 사용하는 회원 관련 기능들")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @Autowired
    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }
    
    @GetMapping("/dept")
    @Operation(
            summary = "본사 직원 본인의 부서 정보 조회",
            description = "userId로 본사 직원 본인의 부서 정보 조회.(부서id, 부서 이름)"
    )
    public ResponseEntity<HqUserDepartmentDTO> getUserDepartment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        HqUserDepartmentDTO hqUserDepartmentDTO = userQueryService.getDepartmentInfo(customUserDetails.getUserId());

        return ResponseEntity.ok(hqUserDepartmentDTO);
    }

    @GetMapping("/hq")
    @Operation(
            summary = "본사 직원 검색",
            description = "부서 id, 이름으로 검색 가능. 본사 직원 정보를 받아옴"
    )
    public ResponseEntity<List<SearchHqUserDTO>> searchHqUser(@RequestParam String name,
                                                            @RequestParam(required = false) Long departmentId) {

        List<SearchHqUserDTO> hqUsers = userQueryService.findHqUser(name, departmentId);

        return ResponseEntity.ok().body(hqUsers);
    }

    @GetMapping("/franchise")
    @Operation(
            summary = "가맹점주 검색",
            description = "이름으로 검색 가능. 가맹점주 및 가맹점 정보를 받아옴"
    )
    public ResponseEntity<List<SearchFranchiseUserDTO>> searchFranchiseUser(@RequestParam String name) {

        List<SearchFranchiseUserDTO> franchiseUsers = userQueryService.findFranchiseUser(name);

        return ResponseEntity.ok().body(franchiseUsers);
    }

    @GetMapping("/supplier")
    @Operation(
            summary = "공급처 직원 검색",
            description = "이름으로 검색 가능. 공급처 직원 및 공급처 정보를 받아옴"
    )
    public ResponseEntity<List<SearchSupplierUserDTO>> searchSupplierUser(@RequestParam String name) {

        List<SearchSupplierUserDTO> supplierUsers = userQueryService.findSupplierUser(name);

        return ResponseEntity.ok().body(supplierUsers);
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "내 정보를 조회합니다."
    )
    public ResponseEntity<MyInfoDTO> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        MyInfoDTO myInfo = userQueryService.getMyInfo(customUserDetails.getUserId());

        return ResponseEntity.ok(myInfo);
    }
}
