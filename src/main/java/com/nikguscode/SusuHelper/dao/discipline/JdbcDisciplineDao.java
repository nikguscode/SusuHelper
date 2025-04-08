package com.nikguscode.SusuHelper.dao.discipline;

import com.nikguscode.SusuHelper.model.entity.Discipline;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class JdbcDisciplineDao implements DisciplineDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Discipline> getAll() {
    String sqlQuery = "SELECT * FROM discipline";
    return jdbcTemplate.query(sqlQuery, new DisciplineRowMapper());
  }

  private static class DisciplineRowMapper implements RowMapper<Discipline> {

    @Override
    public Discipline mapRow(ResultSet rs, int rowNum) throws SQLException {
      return Discipline.builder()
          .id(UUID.fromString(rs.getString("id")))
          .disciplineName(rs.getString("discipline_name"))
          .attendanceUrl(rs.getString("attendance_url"))
          .startTime(rs.getTime("start_time").toLocalTime())
          .endTime(rs.getTime("end_time").toLocalTime())
          .scheduledDays(List.of((String[]) rs.getArray("scheduled_days").getArray()))
          .studentGroupId(UUID.fromString(rs.getString("student_group_id")))
          .disciplineVerificationCodeId(
              UUID.fromString(rs.getString("discipline_verification_code_id")))
          .build();
    }
  }
}