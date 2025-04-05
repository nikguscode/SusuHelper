package com.nikguscode.SusuHelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SusuHelperApplication {

  public static void main(String[] args) {
    SpringApplication.run(SusuHelperApplication.class, args);
  }
}