package com.nikguscode.SusuHelper.model.entity;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Student {

  private final UUID id;
  private final String susuLogin;
  private final String susuPassword;
  private final String susuGroup;
  private final String tgTag;
  private final long tgId;
  private final boolean isBlocked;
  private final StudentRole role;
}