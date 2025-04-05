package com.nikguscode.SusuHelper.service.mark;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HttpMarkService implements MarkService {

  @Qualifier("httpAuthService")
  private final AuthService authService;

  public HttpMarkService(AuthService authService) {
    this.authService = authService;
  }

 // @Scheduled(fixedDelay = 900000)
  @Override
  public void mark() {
    authService.auth();
  }
}