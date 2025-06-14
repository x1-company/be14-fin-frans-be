package com.x1.frans.returns.command.domain.vo;

import com.x1.frans.returns.command.domain.aggregate.ReturnDetailEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "반품 등록 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnCreateRequestVO {

    @Schema(description = "반품 사유")
    private String description;

    @Schema(description = "반품 상세 정보")
    private List<ReturnDetailRequestVO> details;

    @Schema(description = "반품 첨부 파일 url")
    private List<String> fileUrls;

}
