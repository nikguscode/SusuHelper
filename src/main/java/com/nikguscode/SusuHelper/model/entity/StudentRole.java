package com.nikguscode.SusuHelper.model.entity;

import lombok.Getter;

@Getter
public enum StudentRole {

  USER("USER"),
  MODERATOR1("MODERATOR1"),
  MODERATOR2("MODERATOR2"),
  ADMIN("ADMIN");

  private final String role;

  private StudentRole(String role) {
    this.role = role;
  }
}