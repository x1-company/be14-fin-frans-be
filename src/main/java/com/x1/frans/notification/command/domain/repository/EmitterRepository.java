package com.x1.frans.notification.command.domain.repository;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {

    // Emitter 저장
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    // 이벤트 캐시 저장
    void saveEventCache(String eventCacheId, Object event);

    // 특정 회원 ID로 시작하는 emitter 조회
    // 현재 연결된 SSE emitter들을 찾기 위해 사용
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

    // 특정 회원 ID로 시작하는 이벤트 캐시 조회
    // 이전 연결에서 클라이언트가 못 받은 알림들을 재전송하기 위해 사용
    Map<String, Object> findAllEventCacheStartWithByUserId(String userId);

    // 특정 emitter 삭제
    void deleteById(String id);

    // 특정 회원 ID로 시작하는 모든 emitter 삭제
    void deleteAllEmitterStartWithId(String userId);

    // 특정 회원 ID로 시작하는 모든 이벤트 캐시 삭제
    void deleteAllEventCacheStartWithId(String userId);

    boolean exists(String emitterId);

}
