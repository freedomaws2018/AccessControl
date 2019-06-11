package com.example.demo.Common;

import java.io.FileOutputStream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {

  /** POST **/
  public static void doPost(String url) {
    new RestTemplate().exchange(url, HttpMethod.POST, null, String.class);
  }

  public static Integer doPost(HttpHeaders headers, String url) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", String.format("Bearer %s", channelAccessToken));

    ResponseEntity<String> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.POST, new HttpEntity<>("", headers), String.class);

    return responseEntity.getStatusCodeValue();
  }

  public static <T> T doPost(HttpHeaders headers, String url, Class<T> responseType) {
    ResponseEntity<T> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.POST, new HttpEntity<>("", headers), responseType);

    return responseEntity.getBody();
  }

  public static <T> T doPostWithBody(HttpHeaders headers, String url, String jsonBody, Class<T> responseType) {
    ResponseEntity<T> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.POST, new HttpEntity<>(jsonBody, headers), responseType);

    return responseEntity.getBody();
  }

  public static Boolean doPostWithImage(HttpHeaders headers, String url, byte[] image) {
    ResponseEntity<String> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.POST, new HttpEntity<>(image, headers), String.class);
    return responseEntity.getStatusCodeValue() == 200;
  }

  /** GET **/

  public static String doGet(String url) {

    ResponseEntity<String> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.GET, null, String.class);

    return responseEntity.getBody();
  }

  public static String doGet(HttpHeaders headers, String url) {
    // HttpHeaders headers = new HttpHeaders();
    // headers.add("Content-Type", "application/json");
    // headers.add("Accept", "*/*");
    ResponseEntity<String> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), String.class);
    System.err.println(responseEntity);
    return responseEntity.getBody();
  }

  public static <T> T doGet(HttpHeaders headers, String url, Class<T> responseType) {
    ResponseEntity<T> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), responseType);

    return responseEntity.getBody();
  }

  public static byte[] doGetImage(HttpHeaders headers, String url) {
    ResponseEntity<byte[]> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), byte[].class);

    return responseEntity.getBody();
  }

  public static String doGetImageBase64(HttpHeaders headers, String url) {
    String base64Encoded = null;
    try {
      ResponseEntity<byte[]> responseEntity = new RestTemplate() //
          .exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), byte[].class);
      byte[] encodeBase64 = Base64Utils.encode(responseEntity.getBody());
      base64Encoded = new String(encodeBase64, "UTF-8");
    } catch (Exception ex) {

    }
    return base64Encoded;
  }

  @Deprecated
  public static void doGetDownloadImage(HttpHeaders headers, String url, String filePath) throws Exception {

    ResponseEntity<byte[]> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.GET, new HttpEntity<>("", headers), byte[].class);

    FileOutputStream imgOutFile = new FileOutputStream("static/images/richMenu/" + filePath);
    imgOutFile.write(responseEntity.getBody());
    imgOutFile.close();

  }

  /** DELETE **/
  public static Integer doDelete(HttpHeaders headers, String url) {

    ResponseEntity<String> responseEntity = new RestTemplate() //
        .exchange(url, HttpMethod.DELETE, new HttpEntity<>("", headers), String.class);

    return responseEntity.getStatusCodeValue();
  }

}
