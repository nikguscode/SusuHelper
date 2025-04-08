package com.nikguscode.SusuHelper.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @PostMapping(
      value = "/users",
      consumes = "application/json")
  public void handle() {
    System.out.println("Принял!");
  }
//
//  @DeleteMapping("/users")
//  public void handle() {
//
//  }
//
//  @PatchMapping("/users")
//  public void handle() {
//
//  }
//
//  @GetMapping("/users")
//  public void handle() {
//
//  }
}