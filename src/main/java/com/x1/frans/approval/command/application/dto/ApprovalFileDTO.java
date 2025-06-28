package com.x1.frans.approval.command.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalFileDTO {

    @Schema(description = "파일명")
    private String name;

    @Schema(description = "파일 경로 또는 URL")
    private String url;

    private Integer size;

}
