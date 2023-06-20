package com.themoment.everygsm.global.redis.repository;

import com.themoment.everygsm.global.redis.Entity.LockObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(final Long projectId, Long userId) {

        return redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(projectId, userId), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(LockObject lockObject) {
        return redisTemplate.delete(generateKey(lockObject.getProjectId(), lockObject.getUserId()));
    }

    private String generateKey(final Long projectId, final Long userId) {
        LockObject lockObject = LockObject.builder()
                .projectId(projectId)
                .userId(userId)
                .build();
        return lockObject.toString();
    }
}
