package com.x1.frans.purchase.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PurchaseRequestStatusConverter implements AttributeConverter<PurchaseRequestStatus, String> {
    @Override
    public String convertToDatabaseColumn(PurchaseRequestStatus status) {
        return status != null ? status.getLabel() : null;
    }

    @Override
    public PurchaseRequestStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (PurchaseRequestStatus status : PurchaseRequestStatus.values()) {
            if (status.getLabel().equals(dbData)) return status;
        }
        throw new IllegalArgumentException("Unknown status label: " + dbData);
    }
}