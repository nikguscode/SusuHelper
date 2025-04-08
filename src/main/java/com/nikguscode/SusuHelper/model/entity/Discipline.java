package com.nikguscode.SusuHelper.model.entity;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Discipline {

  private final UUID id;
  private final String disciplineName;
  private final String attendanceUrl;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final List<String> scheduledDays;
  private final UUID studentGroupId;
  private final UUID disciplineVerificationCodeId;
}