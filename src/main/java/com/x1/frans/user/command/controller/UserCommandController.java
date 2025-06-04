package com.x1.frans.user.command.controller;

import com.x1.frans.user.command.service.UserCommandService;
import com.x1.frans.user.command.vo.FranchiseUserRequestVO;
import com.x1.frans.user.command.vo.HqUserRequestVO;
import com.x1.frans.user.command.vo.SupplierUserRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @Autowired
    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/hq")
    public ResponseEntity<Void> createHqUser(@RequestBody HqUserRequestVO hqUserRequestVO) {
        userCommandService.createHqUser(hqUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/franchise")
    public ResponseEntity<Void> createFranchiseUser(@RequestBody FranchiseUserRequestVO franchiseUserRequestVO) {
        userCommandService.createFranchiseUser(franchiseUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/supplier")
    public ResponseEntity<Void> createSupplierUser(@RequestBody SupplierUserRequestVO supplierUserRequestVO) {
        userCommandService.createSupplierUser(supplierUserRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
