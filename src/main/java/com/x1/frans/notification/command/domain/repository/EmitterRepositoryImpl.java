package com.x1.frans.notification.command.domain.repository;

import com.x1.frans.notification.command.domain.aggregate.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@Slf4j
public class EmitterRepositoryImpl implements EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    // emitter를 저장
    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    // 이벤트를 저장
    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    // 해당 회원과 관련된 모든 이벤트를 찾음
    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByUserId(String userId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // emitter를 지움
    @Override
    public void deleteById(String id) {
        emitters.remove(id);
    }

    // 해당 회원과 관련된 모든 emitter를 제거 (종료된 연결 등 정리)
    @Override
    public void deleteAllEmitterStartWithId(String userId) {
        emitters.keySet().removeIf(key -> key.startsWith(userId));
    }

    // 캐싱된 이벤트 중 해당 사용자의 것만 제거 (이벤트 유실된 경우 대비 캐시된 것 제거)
    @Override
    public void deleteAllEventCacheStartWithId(String userId) {

        eventCache.keySet().removeIf(key -> key.startsWith(userId));
    }

    @Override
    public boolean exists(String emitterId) {
        return emitters.containsKey(emitterId);
    }

    @Override
    public void deleteEventCacheByNotificationId(String userId, Long notificationId) {
        try {
            Map<String, Object> eventCaches = findAllEventCacheStartWithByUserId(userId);
            List<String> keysToRemove = new ArrayList<>();

            for (Map.Entry<String, Object> entry : eventCaches.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Notification) {
                    Notification n = (Notification) value;
                    if (n.getId().equals(notificationId)) {
                        keysToRemove.add(entry.getKey());
                    }
                }
            }

            // 별도로 삭제하여 ConcurrentModificationException 방지
            keysToRemove.forEach(eventCache::remove);

        } catch (Exception e) {
            log.error("이벤트 캐시 삭제 중 에러: userId={}, notificationId={}", userId, notificationId, e);
        }
    }

}
