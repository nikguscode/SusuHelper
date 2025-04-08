package com.nikguscode.SusuHelper.dao.studentgroup;

import com.nikguscode.SusuHelper.model.entity.StudentGroup;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class JdbcStudentGroupDao implements StudentGroupDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<StudentGroup> getAll() {
    String sqlQuery = "SELECT * FROM user_group";
    return jdbcTemplate.query(sqlQuery, new StudentGroupRowMapper());
  }

  private static class StudentGroupRowMapper implements RowMapper<StudentGroup> {

    @Override
    public StudentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
      return StudentGroup.builder()
          .id(UUID.fromString(rs.getString("id")))
          .disciplineId(UUID.fromString(rs.getString("discipline_id")))
          .groupCode(rs.getString("group_code"))
          .build();
    }
  }
}