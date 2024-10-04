package com.ssafyhome.model.dto.oauth2response;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

  private final Map<String, Object> attributes;

  public KakaoResponse(Map<String, Object> attributes) {

    this.attributes = attributes;
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

  @Override
  public String getProviderId() {
    return attributes.get("sub").toString();
  }

  @Override
  public String getEmail() {
    return attributes.get("email").toString();
  }

  @Override
  public String getName() {
    return attributes.get("name").toString();
  }
}
