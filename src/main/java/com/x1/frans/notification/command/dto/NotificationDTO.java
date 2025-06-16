package com.x1.frans.notification.command.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.x1.frans.notification.command.domain.aggregate.Notification;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class NotificationDTO {

    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String name;
        private String content;
        private String type;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime readAt;

        public static Response createResponse(Notification notification) {
            return Response.builder()
                    .id(notification.getId())
                    .name(notification.getReceiver().getName())
                    .content(notification.getContent())
                    .type(notification.getNotificationType().name())
                    .createdAt(notification.getCreatedAt())
                    .readAt(notification.getReadAt())
                    .build();
        }
    }
    
}
