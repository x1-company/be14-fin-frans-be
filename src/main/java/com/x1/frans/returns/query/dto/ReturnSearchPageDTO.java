package com.x1.frans.returns.query.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
