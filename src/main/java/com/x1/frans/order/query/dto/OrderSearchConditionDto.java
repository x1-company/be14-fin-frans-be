package com.x1.frans.order.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderSearchConditionDto {
    private Long franchiseId;
    private Long userId;
    private Long ownerId;
    private String code;
    private String product;
    private List<String> statusList;
    private String startDate;   // yyyy-MM-dd
    private String endDate;
    private int page;
    private int size;           // 한 페이지당 목록 개수
    private int offset;         // size * (page -1)
}
