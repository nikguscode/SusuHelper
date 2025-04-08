package com.nikguscode.SusuHelper.dto.request.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

  private final String susuLogin;
  private final String susuPassword;
  private final String susuGroup;
}