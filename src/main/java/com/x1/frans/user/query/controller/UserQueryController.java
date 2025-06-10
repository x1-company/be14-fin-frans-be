package com.x1.frans.user.query.controller;

import com.x1.frans.user.query.dto.SearchFranchiseUserDTO;
import com.x1.frans.user.query.dto.SearchHqUserDTO;
import com.x1.frans.user.query.dto.SearchSupplierUserDTO;
import com.x1.frans.user.query.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hq/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @Autowired
    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/hq")
    public ResponseEntity<List<SearchHqUserDTO>> searchHqUser(@RequestParam String name,
                                                            @RequestParam(required = false) Long departmentId) {

        List<SearchHqUserDTO> hqUsers = userQueryService.findHqUser(name, departmentId);

        return ResponseEntity.ok().body(hqUsers);
    }

    @GetMapping("/franchise")
    public ResponseEntity<List<SearchFranchiseUserDTO>> searchFranchiseUser(@RequestParam String name) {

        List<SearchFranchiseUserDTO> franchiseUsers = userQueryService.findFranchiseUser(name);

        return ResponseEntity.ok().body(franchiseUsers);
    }

    @GetMapping("/supplier")
    public ResponseEntity<List<SearchSupplierUserDTO>> searchSupplierUser(@RequestParam String name) {

        List<SearchSupplierUserDTO> supplierUsers = userQueryService.findSupplierUser(name);

        return ResponseEntity.ok().body(supplierUsers);
    }
}
