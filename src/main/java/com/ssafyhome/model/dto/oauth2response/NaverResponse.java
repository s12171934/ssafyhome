package com.ssafyhome.model.dto.oauth2response;

import java.util.Map;

public class NaverResponse implements OAuth2Response {

  private final Map<String, Object> attributes;

  public NaverResponse(Map<String, Object> attributes) {

    this.attributes = attributes;
  }

  @Override
  public String getProvider() {
    return "naver";
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
