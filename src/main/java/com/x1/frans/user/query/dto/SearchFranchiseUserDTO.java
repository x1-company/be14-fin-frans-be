package com.x1.frans.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchFranchiseUserDTO extends SearchUserDTO {

    private Long franchiseId;
    private String franchiseCode;
    private String franchiseName;
    private String franchiseAddress;
    private String franchiseAddressDetail;
    private String franchiseZipcode;
    private String franchiseBusinessNumber;
    private String franchisePhone;
    private LocalDateTime franchiseSignedAt;
}
