package com.nikguscode.SusuHelper.service.mark;

import static com.nikguscode.SusuHelper.config.RegexConstants.GET_URL_FROM_ATTENDANCE_BUTTON_REGEX;
import static com.nikguscode.SusuHelper.config.RegexConstants.RADIO_BUTTON_ID_REGEX;

import com.nikguscode.SusuHelper.dao.discipline.DisciplineDao;
import com.nikguscode.SusuHelper.dao.student.StudentDao;
import com.nikguscode.SusuHelper.dao.studentgroup.StudentGroupDao;
import com.nikguscode.SusuHelper.model.entity.Discipline;
import com.nikguscode.SusuHelper.model.entity.Student;
import com.nikguscode.SusuHelper.model.entity.StudentGroup;
import com.nikguscode.SusuHelper.service.auth.AuthService;
import com.nikguscode.SusuHelper.service.filter.MarkFilter;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HttpMarkService implements MarkService {

  @Qualifier("httpAuthService")
  private final AuthService authService;

  @Qualifier("jdbcDisciplineDao")
  private final DisciplineDao disciplineDao;

  @Qualifier("jdbcUserDao")
  private final StudentDao studentDao;

  @Qualifier("jdbcStudentGroupDao")
  private final StudentGroupDao studentGroupDao;

  @Qualifier("localMarkFilter")
  private final MarkFilter markFilter;

  private final RestClient restClient;

  public HttpMarkService(
      AuthService authService,
      DisciplineDao disciplineDao,
      StudentDao studentDao,
      StudentGroupDao studentGroupDao,
      RestClient restClient,
      MarkFilter markFilter) {
    this.authService = authService;
    this.disciplineDao = disciplineDao;
    this.studentDao = studentDao;
    this.studentGroupDao = studentGroupDao;
    this.restClient = restClient;
    this.markFilter = markFilter;
  }

  @Scheduled(fixedDelay = 1_800_000)
  @Override
  public void mark() throws AuthenticationException {
    List<Discipline> filteredDisciplines = markFilter.filterByCurrentDayOfWeekAndTime(
        disciplineDao.getAll());
    List<Student> students = studentDao.getAll();
    List<StudentGroup> groups = studentGroupDao.getAll();

    if (filteredDisciplines.isEmpty()) {
      return;
    }

    for (Discipline discipline : filteredDisciplines) {
      Set<Student> currentDisciplineStudents = markFilter.filterByDisciplineStudentGroup(
          discipline, groups, students);

      for (Student student : currentDisciplineStudents) {
        sendMarkRequest(student, discipline);
      }
    }
  }

  private void sendMarkRequest(Student student, Discipline discipline)
      throws AuthenticationException {
    String cookie = authService.auth(student);
    String requestUrl = getUrlFromAttendanceButton(discipline, cookie);
    String requestBody = generateHttpRequestBody(cookie, requestUrl);

    ResponseEntity<String> request = restClient.post()
        .uri(requestUrl)
        .header(HttpHeaders.COOKIE, cookie)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(requestBody)
        .retrieve()
        .toEntity(String.class);
  }

  private String generateHttpRequestBody(String cookie, String requestUrl) {
    ResponseEntity<String> response = restClient.get()
        .uri(requestUrl)
        .header(HttpHeaders.COOKIE, cookie)
        .accept(MediaType.TEXT_HTML)
        .retrieve()
        .toEntity(String.class);
    long radioButtonId = extractRadioButtonId(response.getBody());
    return String.format(
        "_qf__mod_attendance_form_studentattendance=1"
            + "&mform_isexpanded_id_session=1"
            + "&status=%d"
            + "&submitbutton=Сохранить",
        radioButtonId
    );
  }

  private String getUrlFromAttendanceButton(Discipline discipline, String cookie) {
    ResponseEntity<String> response = restClient.get()
        .uri(discipline.getAttendanceUrl())
        .header(HttpHeaders.COOKIE, cookie)
        .accept(MediaType.TEXT_HTML)
        .retrieve()
        .toEntity(String.class);
    return extractUrlFromAttendanceButton(response.getBody());
  }

  private long extractRadioButtonId(String html) {
    Pattern pattern = Pattern.compile(RADIO_BUTTON_ID_REGEX);
    Matcher matcher = pattern.matcher(html);

    if (matcher.find()) {
      return Long.parseLong(matcher.group(1));
    }

    throw new IllegalStateException(
        "Exception when extracting id from radio button for attendance");
  }

  private String extractUrlFromAttendanceButton(String html) {
    Pattern pattern = Pattern.compile(GET_URL_FROM_ATTENDANCE_BUTTON_REGEX);
    Matcher matcher = pattern.matcher(html);
    System.out.println(html);
    if (matcher.find()) {
      return matcher.group(1).replace("&amp;", "&");
    }

    throw new IllegalStateException(
        "Exception when extracting link from submit button for attendance");
  }
}