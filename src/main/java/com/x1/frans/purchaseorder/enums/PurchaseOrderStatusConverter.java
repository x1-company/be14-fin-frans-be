package com.x1.frans.purchaseorder.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PurchaseOrderStatusConverter implements AttributeConverter<PurchaseOrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(PurchaseOrderStatus attribute) {
        if (attribute == null) return null;
        return attribute.getLabel();
    }

    @Override
    public PurchaseOrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (PurchaseOrderStatus status : PurchaseOrderStatus.values()) {
            if (status.getLabel().equals(dbData)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + dbData);
    }
}