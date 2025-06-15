package com.x1.frans.approval.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalResponseDTO {

    private Long id;
    private String code;
    private LocalDateTime createdAt;


}
