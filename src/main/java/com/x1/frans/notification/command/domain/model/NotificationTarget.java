package com.x1.frans.notification.command.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTarget {

    @Enumerated(EnumType.STRING)
    private NotificationDomainType domainType;
    private Long targetId;
    private String additionalInfo;

}
