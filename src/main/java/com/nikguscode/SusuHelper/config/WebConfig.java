package com.nikguscode.SusuHelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class WebConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.builder()
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
        .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
        .defaultStatusHandler(
            HttpStatusCode::is3xxRedirection,
            (request, response) -> {} // Игнорируем, так как RestClient сам обработает редирект
        )
        .build();
  }
}