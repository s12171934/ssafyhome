package com.ssafyhome.util;

import com.ssafyhome.model.dao.repository.EmailSecretRepository;
import com.ssafyhome.model.dto.entity.redis.EmailSecretEntity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SecretUtil {

	private final EmailSecretRepository emailSecretRepository;

	public SecretUtil(EmailSecretRepository emailSecretRepository) {

		this.emailSecretRepository = emailSecretRepository;
	}

	public String makeRandomString(int length) {

		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			int random = new Random().nextInt(26 + 26 + 10);
			switch (random / 26) {
				case 0 -> random += 65;
				case 1 -> random += 97 - 26;
				case 2 -> random += 48 - 52;
			}
			chars[i] = (char)random;
		}
		return String.valueOf(chars);
	}

	public void addSecretOnRedis(String email, String secret) {

		if(emailSecretRepository.existsById(email)) emailSecretRepository.deleteById(email);
		emailSecretRepository.save(new EmailSecretEntity(email, secret));
	}

	public boolean checkSecret(String email, String secret) {

		String redisSecret = emailSecretRepository.findById(email).get().getSecret();
		return secret.equals(redisSecret);
	}
}
