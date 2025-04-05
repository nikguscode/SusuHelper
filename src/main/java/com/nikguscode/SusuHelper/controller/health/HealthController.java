package com.nikguscode.SusuHelper.controller.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping("/health")
  public String handle() {
    return "Health :)";
  }
}