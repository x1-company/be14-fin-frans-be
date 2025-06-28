package com.x1.frans.returns.command.domain.vo;

import com.x1.frans.returns.enums.ProductReturnStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "자재별 반품 상태")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnDetailStatusVO {

    private Long returnDetailId;
    private ProductReturnStatus status;
}
