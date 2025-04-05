package com.nikguscode.SusuHelper.controller.health;

import com.nikguscode.SusuHelper.service.mark.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class CookieHealthController {

  @Qualifier("httpAuthService")
  private final AuthService authService;
  private final RestClient restClient;

  public CookieHealthController(AuthService authService, RestClient restClient) {
    this.authService = authService;
    this.restClient = restClient;
  }

  @GetMapping("/auth/health")
  public String handle() {
    String sessionCookie = authService.auth();
    ResponseEntity<String> response = restClient.get()
        .uri("https://edu.susu.ru/my/courses.php")
        .header(HttpHeaders.COOKIE, sessionCookie)
        .retrieve()
        .toEntity(String.class);
    return response.getBody();
  }
}