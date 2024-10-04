package com.ssafyhome.model.dto;

import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomOAuth2User implements OAuth2User {

	private final UserEntity user;

	public CustomOAuth2User(UserEntity user) {

		this.user = user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});

		return authorities;
	}

	@Override
	public String getName() {
		return user.getUserId();
	}

	public String getEmail() {return user.getUserEmail(); }
}
