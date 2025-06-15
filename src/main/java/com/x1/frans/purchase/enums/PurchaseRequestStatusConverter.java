package com.x1.frans.purchase.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PurchaseRequestStatusConverter implements AttributeConverter<PurchaseRequestStatus, String> {
    @Override
    public String convertToDatabaseColumn(PurchaseRequestStatus attribute) {
        if (attribute == null) return null;
        return attribute.getLabel();
    }

    @Override
    public PurchaseRequestStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (PurchaseRequestStatus status : PurchaseRequestStatus.values()) {
            if (status.getLabel().equals(dbData)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + dbData);
    }
}