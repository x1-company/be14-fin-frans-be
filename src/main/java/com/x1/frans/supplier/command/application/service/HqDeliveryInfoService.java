package com.x1.frans.supplier.command.application.service;

import jakarta.validation.constraints.NotNull;

public interface HqDeliveryInfoService {

    void confirmDeliveryDate(Long id, @NotNull Integer year, @NotNull Integer month, @NotNull Integer day);

}
