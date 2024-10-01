package com.ssafyhome.model.dto.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "email-secret", timeToLive = 5 * 60)
public class EmailSecretEntity {
}
