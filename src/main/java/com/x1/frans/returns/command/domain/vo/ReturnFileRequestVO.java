package com.x1.frans.returns.command.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "반품 첨부파일 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnFileRequestVO {

    @Schema(description = "파일 url")
    private String url;

    @Schema(description = "파일 명")
    private String name;

}
