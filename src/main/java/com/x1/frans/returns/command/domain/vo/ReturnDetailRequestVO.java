package com.x1.frans.returns.command.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.connection.ReturnType;

@Schema(description = "반품 등록 상세 정보")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReturnDetailRequestVO {

    @Schema(description = "반품 타입")
    private ReturnType returnType;

    @Schema(description = "반품 수량")
    private Integer quantity;

    @Schema(description = "자재 ID")
    private Long productId;

    @Schema(description = "주문 ID")
    private Long orderId;

}
