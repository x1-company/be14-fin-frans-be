package com.x1.frans.returns.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReturnSearchConditionDTO {
    private Long franchiseId;
    private String code;
    private String name;
    private String product;
    private String status;
    private LocalDate startDate;   // yyyy-MM-dd
    private LocalDate endDate;
    private int page;
    private int size;           // 한 페이지당 목록 개수
    private int offset;         // size * (page -1)

    private List<Long> FranchiseIds; // 권한 필터링 (본사: 부서 권한, 가맹점: 가맹점주 체크)
}
