package com.x1.frans.approval.query.dto.Detail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "첨부파일 조회 DTO")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalFileDTO {

    @Schema(description = "파일 ID")
    private Long fileId;

    @Schema(description = "파일명")
    private String fileName;

    @Schema(description = "url")
    private String url;
}
