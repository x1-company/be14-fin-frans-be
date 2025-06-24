package com.x1.frans.order.command.application.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderTemplateRequestDTO {

    private String name;
    private String description;
    private int seq;
    private List<OrderTemplateDetailDTO> details;
}
