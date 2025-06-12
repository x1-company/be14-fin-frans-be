package com.x1.frans.user.command.vo;

import com.x1.frans.user.command.aggregate.UserEntity;
import com.x1.frans.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateUserRequestVO {

    private String name;
    private String email;
    private String phone;
    private String userType;

    public UserEntity toUserEntity(String userCode, String encryptedPassword) {
        UserEntity user = new UserEntity();
        user.setCode(userCode);
        user.setPassword(encryptedPassword);
        user.setName(this.name);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        user.setType(UserType.valueOf(this.userType));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsTempPassword(true);
        return user;
    }
}
