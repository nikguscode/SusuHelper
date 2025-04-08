package com.nikguscode.SusuHelper.config;

public class RegexConstants {

  public static final String GET_URL_FROM_ATTENDANCE_BUTTON_REGEX =
      "<a\\b[^>]*href\\s*=\\s*[\"']([^\"']*)[\"'][^>]*>\\s*Отправить посещаемость\\s*</a>";
  public static final String RADIO_BUTTON_ID_REGEX =
      "(?s)<label[^>]*>.*?<input[^>]*value\\s*=\\s*\"([^\"]+)\"[^>]*>.*?<span[^>]*>Присутствовал</span>";
}
