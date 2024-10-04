package com.ssafyhome.model.service.impl;

import com.ssafyhome.model.dao.mapper.UserMapper;
import com.ssafyhome.model.dto.CustomOAuth2User;
import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import com.ssafyhome.model.dto.oauth2response.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private final UserMapper userMapper;
	
	public CustomOAuth2UserService(UserMapper userMapper) {
		
		this.userMapper = userMapper;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2Response oAuth2Response = switch (registrationId) {
			case "google" -> new GoogleResponse(oAuth2User.getAttributes());
			case "naver" -> new NaverResponse(oAuth2User.getAttributes());
			case "kakao" -> new KakaoResponse(oAuth2User.getAttributes());
			case "admin" -> new AdminResponse(oAuth2User.getAttributes());
			default -> null;
		};
		if (oAuth2Response == null) return null;

		String userId = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
		UserEntity existUser = userMapper.getUserById(userId);

		if (existUser == null) {

			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(userId);
			userEntity.setUserEmail(oAuth2Response.getEmail());
			userMapper.insertUser(userEntity);
			existUser = userEntity;
		}
		else {
			existUser.setUserEmail(oAuth2Response.getEmail());
			userMapper.updateUser(existUser);
		}
		return new CustomOAuth2User(existUser);
	}
}
