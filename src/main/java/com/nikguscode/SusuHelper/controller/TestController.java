package com.nikguscode.SusuHelper.controller;

import com.nikguscode.SusuHelper.dao.discipline.JdbcDisciplineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired
  private JdbcDisciplineDao jdbcDisciplineDao;

  @GetMapping("/test")
  public String handle() {
    return jdbcDisciplineDao.getAll().toString();
  }
}