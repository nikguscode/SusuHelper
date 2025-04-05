package com.nikguscode.SusuHelper.service.mark;

import com.nikguscode.SusuHelper.dto.request.AuthRequestDto;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

  @Override
  public String auth() {
    ResponseEntity<String> loginPageResponse = restClient.get()
        .uri("https://edu.susu.ru/login/index.php")
        .accept(MediaType.TEXT_HTML)
        .retrieve()
        .toEntity(String.class);

    String loginToken = extractCsrfToken(loginPageResponse.getBody());
    List<String> initialCookies = loginPageResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
    String requestBody = String.format(
        "anchor=&logintoken=%s&username=%s&password=%s",
        loginToken,
        "et2313kgm13",
        "gPBXRJ8T"
    );

    ResponseEntity<String> response = restClient.post()
        .uri("https://edu.susu.ru/login/index.php")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.COOKIE, initialCookies.getFirst())
        .body(requestBody)
        .retrieve()
        .toEntity(String.class);

    List<String> postCookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

    return postCookies.getFirst();
  }

  private String extractCsrfToken(String html) {
    Pattern pattern = Pattern.compile("name=\"logintoken\".*?value=\"(.*?)\"");
    Matcher matcher = pattern.matcher(html);

    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new RuntimeException("CSRF token not found");
  }
}