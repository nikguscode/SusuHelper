package com.nikguscode.SusuHelper.dto.request.from;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({"logintoken", "username", "password"})
public class AuthRequestDto {

  @JsonProperty("logintoken")
  private final String loginToken;

  private final String username;
  private final String password;
}