package com.ssafyhome.model.dto.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "refresh-token", timeToLive = 24 * 60 * 60)
public class RefreshTokenEntity {
}
