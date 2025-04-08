package com.nikguscode.SusuHelper.service.auth;

import com.nikguscode.SusuHelper.model.entity.Student;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HttpAuthService implements AuthService {

  @Autowired
  private RestClient restClient;

  private ResponseEntity<String> processCsrfPageRequest() {
    return restClient.get()
        .uri("https://edu.susu.ru/login/index.php")
        .accept(MediaType.TEXT_HTML)
        .retrieve()
        .toEntity(String.class);
  }

  @Override
  public String auth(Student student) throws AuthenticationException {
    ResponseEntity<String> csrfPageResponse = processCsrfPageRequest();
    String csrfToken = extractCsrfToken(csrfPageResponse.getBody());
    String csrfCookie = csrfPageResponse.getHeaders().get(HttpHeaders.SET_COOKIE).getFirst();

    String requestBody = String.format(
        "anchor=&logintoken=%s&username=%s&password=%s",
        csrfToken,
        student.getSusuLogin(),
        student.getSusuPassword()
    );

    ResponseEntity<String> response = restClient.post()
        .uri("https://edu.susu.ru/login/index.php")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .header(HttpHeaders.COOKIE, csrfCookie)
        .body(requestBody)
        .retrieve()
        .toEntity(String.class);
    return response.getHeaders().get(HttpHeaders.SET_COOKIE).getFirst();
  }

  private String extractCsrfToken(String html) throws AuthenticationException {
    Pattern pattern = Pattern.compile("name=\"logintoken\".*?value=\"(.*?)\"");
    Matcher matcher = pattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new AuthenticationException("CSRF token not found");
  }
}