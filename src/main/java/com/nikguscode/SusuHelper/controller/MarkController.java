package com.nikguscode.SusuHelper.controller;

import com.nikguscode.SusuHelper.service.mark.HttpMarkService;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkController {

  @Autowired
  private HttpMarkService httpMarkService;

  @GetMapping("/mark")
  public String handle() throws AuthenticationException {
    httpMarkService.mark();
    return "no exceptions";
  }
}