package com.x1.frans.approval.query.dto.Detail.lines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class ApprovalLinesDTO {

    @Schema(description = "결재 ID")
    private Long id;

    @Schema(description = "기안자명")
    private String drafterName;

    private String url;

    @Schema(description = "결재선 정보")
    private List<ApprovalLineDTO> line;

}
