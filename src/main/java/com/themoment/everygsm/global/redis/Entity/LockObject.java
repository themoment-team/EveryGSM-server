package com.themoment.everygsm.global.redis.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LockObject {

    private Long projectId;

    private Long userId;
}
