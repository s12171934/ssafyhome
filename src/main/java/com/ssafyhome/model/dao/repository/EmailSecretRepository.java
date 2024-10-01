package com.ssafyhome.model.dao.repository;

import com.ssafyhome.model.dto.entity.redis.EmailSecretEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmailSecretRepository extends CrudRepository<String, EmailSecretEntity> {
}
