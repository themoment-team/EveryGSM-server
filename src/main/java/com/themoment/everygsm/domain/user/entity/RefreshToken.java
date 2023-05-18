package com.themoment.everygsm.domain.user.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("refreshToken")
public class RefreshToken {
    @Id
    private String userEmail;

    @Indexed
    private String token;

    @TimeToLive
    private Long expiredAt;

    public void updateRefreshToken(String token) {
        this.token = token;
    }
}
