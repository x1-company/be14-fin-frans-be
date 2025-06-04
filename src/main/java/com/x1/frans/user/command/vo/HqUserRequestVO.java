package com.x1.frans.user.command.vo;

import com.x1.frans.user.command.aggregate.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HqUserRequestVO extends CreateUserRequestVO {

    private Integer departmentId;
    private Integer positionId;
    private Integer dutyId;
    private String profileUrl;

    @Override
    public UserEntity toUserEntity(String userCode, String encryptedPassword) {
        UserEntity user = super.toUserEntity(userCode, encryptedPassword);
        user.setProfileUrl(this.profileUrl);
        return user;
    }
}
