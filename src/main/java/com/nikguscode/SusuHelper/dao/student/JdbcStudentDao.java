package com.nikguscode.SusuHelper.dao.student;

import com.nikguscode.SusuHelper.dto.request.in.UserDto;
import com.nikguscode.SusuHelper.model.entity.Student;
import com.nikguscode.SusuHelper.model.entity.StudentRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class JdbcStudentDao implements StudentDao {

  private final JdbcTemplate jdbcTemplate;

  public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public ResponseEntity<UserDto> add(UserDto userDto) {
    String sqlQuery =
        "INSERT INTO \"user\" (id, susu_login, susu_password, susu_group_id, is_blocked, api_role)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(
        sqlQuery, UUID.randomUUID(), userDto.getSusuLogin(), userDto.getSusuPassword(),
        userDto.getSusuGroup(), false, "USER");
    return ResponseEntity.ok(userDto);
  }

  @Override
  public List<Student> getAll() {
    String sqlQuery = "SELECT * FROM \"user\"";
    return jdbcTemplate.query(sqlQuery, new UserRowMapper());
  }

  private static class UserRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Student.builder()
          .id(UUID.fromString(rs.getString("id")))
          .susuLogin(rs.getString("susu_login"))
          .susuPassword(rs.getString("susu_password"))
          .susuGroup(rs.getString("susu_group"))
          .tgTag(rs.getString("tg_tag"))
          .tgId(rs.getLong("tg_id"))
          .isBlocked(rs.getBoolean("is_blocked"))
          .role(StudentRole.valueOf(rs.getString("api_role")))
          .build();
    }
  }
}