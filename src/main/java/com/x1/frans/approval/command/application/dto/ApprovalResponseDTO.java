package com.x1.frans.approval.command.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApprovalResponseDTO {

    private Long id;
    private String code;
    private String name;
    private LocalDateTime createdAt;




    public ApprovalResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ApprovalResponseDTO(Long id, String code, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.createdAt = createdAt;
    }
}
