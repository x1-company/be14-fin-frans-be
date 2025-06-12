package com.x1.frans.product.command.application.service;

import com.x1.frans.product.command.application.service.dto.ProductCreateCommand;
import com.x1.frans.product.command.application.service.dto.ProductUpdateCommand;

public interface ProductCommandService {

    long create(ProductCreateCommand command);
    void update(ProductUpdateCommand command);
    void changeActive(long id, boolean active);
}
