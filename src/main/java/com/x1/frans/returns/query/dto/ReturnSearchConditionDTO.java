package com.x1.frans.returns.query.dto;

import com.x1.frans.returns.enums.ReturnStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "반품 검색 필터링 조건")
@Getter
@Setter
@NoArgsConstructor
public class ReturnSearchConditionDTO {
    private Long franchiseId;
    private String code;
    private String name;
    private String product;
    private ReturnStatus status;
    private LocalDate startDate;   // yyyy-MM-dd
    private LocalDate endDate;
    private int page;
    private int size;           // 한 페이지당 목록 개수
    private int offset;         // size * (page -1)

    private List<Long> FranchiseIds; // 권한 필터링 (본사: 부서 권한, 가맹점: 가맹점주 체크)
}
