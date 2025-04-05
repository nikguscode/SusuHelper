package com.nikguscode.SusuHelper.controller;

import com.nikguscode.SusuHelper.service.mark.AuthService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class MarkController {

  private final RestClient restClient;

  @Qualifier("httpAuthService")
  private final AuthService authService;

  public MarkController(RestClient restClient, AuthService authService) {
    this.restClient = restClient;
    this.authService = authService;
  }

  private String getUrl(String cookie) {
    ResponseEntity<String> response = restClient.get()
        .uri("https://edu.susu.ru/mod/attendance/view.php?id=8031794")
        .header(HttpHeaders.COOKIE, cookie)
        .accept(MediaType.TEXT_HTML)
        .retrieve()
        .toEntity(String.class);
    String body = response.getBody();
    Pattern pattern = Pattern.compile("<a\\b[^>]*href\\s*=\\s*[\"']([^\"']*)[\"'][^>]*>\\s*Отправить посещаемость\\s*</a>");
    Matcher matcher = pattern.matcher(body);

    if (matcher.find()) {
      String url = matcher.group(1);
      System.out.println("URL: " + url);
      url = url.replace("&amp;", "&");
      return url;
    }
    throw new RuntimeException("URL not found");
  }

  private String getBody(String cookie) {
//    String url = getUrl(cookie);
//
//    String[] urlParts = url.split("\\?", 2); // Разделяем URL на базовую часть и параметры
//    String params = urlParts.length > 1 ? urlParts[1] : ""; // Берём часть после "?"
//
//    String[] pairs = params.split("&amp;|&"); // Разделяем параметры по & или &amp;
//    String sessid = "";
//    String sesskey = "";
//
//    for (String pair : pairs) {
//      if (pair.startsWith("sessid=")) {
//        sessid = pair.split("=", 2)[1].trim(); // Берём значение после "sessid="
//      } else if (pair.startsWith("sesskey=")) {
//        sesskey = pair.split("=", 2)[1].trim(); // Берём значение после "sesskey="
//      }
//    }

    StringBuilder sb = new StringBuilder();
//    sb.append("sessid=" + sessid);
//    sb.append("&sesskey=" + sesskey);
    sb.append("_qf__mod_attendance_form_studentattendance=1");
    sb.append("&mform_isexpanded_id_session=1");
    sb.append("&status=824519");
    sb.append("&submitbutton=Сохранить");

//    try {
//      sb.append("&submitbutton=").append(URLEncoder.encode("Сохранить", "UTF-8"));
//    } catch (UnsupportedEncodingException e) {
//      e.printStackTrace(); // Обработка исключения
//    }
    System.out.println("body: " + sb.toString());
    return sb.toString();
  }

  private void sendRequest(String cookie, String url, String body) {
    System.out.println("SEND URL: " + url);

    restClient.post()
        .uri(url)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .header(HttpHeaders.COOKIE, cookie)
        .body(body)
        .retrieve()
        .toBodilessEntity();
  }

  @GetMapping("/mark")
  public String handle() {
    String cookie = authService.auth();
    String url = getUrl(cookie);
    String urlEncodedBody = getBody(cookie);
    sendRequest(cookie, url, urlEncodedBody);
    return "no exceptions";
  }
}