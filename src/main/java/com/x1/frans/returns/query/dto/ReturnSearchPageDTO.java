package com.x1.frans.returns.query.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "반품 목록 조회 페이징 정보")
@Getter
@Builder
public class ReturnSearchPageDTO {
    private List<ReturnListDTO> list;
    private int totalCount;
    private int page;
    private int size;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
