package com.nikguscode.SusuHelper.model.entity;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class StudentGroup {

  private final UUID id;
  private final UUID disciplineId;
  private final String groupCode;
}