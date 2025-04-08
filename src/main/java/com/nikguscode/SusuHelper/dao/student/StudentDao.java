package com.nikguscode.SusuHelper.dao.student;

import com.nikguscode.SusuHelper.dto.request.in.UserDto;
import com.nikguscode.SusuHelper.model.entity.Student;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface StudentDao {

  ResponseEntity<UserDto> add(UserDto userDto);

  List<Student> getAll();
//  ResponseEntity<Void> delete(String susuLogin);

//  ResponseEntity<UserDto> update(UserDto userDto);

//  ResponseEntity<UserDto> get(String susuLogin);
}