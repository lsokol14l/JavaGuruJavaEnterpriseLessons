package by.michael.http.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpExample {
  static void main() throws IOException, InterruptedException {
    var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    HttpRequest request =
        HttpRequest.newBuilder(URI.create("https://www.google.com")).GET().build();

    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.body());

    //    showIp();
  }

  public static void showIp() throws IOException, InterruptedException {
    // Запрос к внешнему сервису, который возвращает ваш публичный IP (например, когда вы за NAT)
    var client = java.net.http.HttpClient.newHttpClient();
    var req =
        java.net.http.HttpRequest.newBuilder(java.net.URI.create("https://api.ipify.org"))
            .GET()
            .build();
    var resp = client.send(req, java.net.http.HttpResponse.BodyHandlers.ofString());
    System.out.println("Public IP: " + resp.body());
  }
}
