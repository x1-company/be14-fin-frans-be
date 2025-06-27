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
        /* 설명.
         *  위 코드는 ConcurrentHashMap을 forEach로 순회하면서 동시에 remove() 를 호출한다.
         *  이 경우 ConcurrentModifiedException은 발생하지 않더라도, 예기치 못한 동작이나 일부 항목이 삭제되지 않는
         *  버그가 생길 수 있다.
         *  특히 멀티스레드 환경에서는 Race Condition이 발생할 수 있다.
         *  아래와 같이 변경하면, ConcurrentHashMap.keySet()은 뷰 객체를 반환하고, removeIf는 내부적으로 안전하게 동작한다.
         *  이 방식은 삭제 중간에 키셋이 변경 되어도 안정적으로 처리할 수 있는 구조다.
        * */
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

//    @Override
//    public void deleteEventCacheByNotificationId(String userId, Long notificationId) {
//        Map<String, Object> eventCaches = findAllEventCacheStartWithByUserId(userId);
//        for (Map.Entry<String, Object> entry : eventCaches.entrySet()) {
//            Object value = entry.getValue();
//            if (value instanceof Notification) {
//                Notification n = (Notification) value;
//                if (n.getId().equals(notificationId)) {
//                    // eventCacheMap → eventCache로 수정
//                    eventCache.remove(entry.getKey());
//                }
//            }
//        }
//    }

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
