package com.nikguscode.SusuHelper.service.auth;

import com.nikguscode.SusuHelper.model.entity.Student;
import javax.naming.AuthenticationException;

public interface AuthService {

  String auth(Student student) throws AuthenticationException;
}