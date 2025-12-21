package by.michael.http.task2Medium;

// # Задание 4*
//
// 1) Написать HTTP Server
// 2) Написать HTTP Client
// 3) Клиент отправляет json файл и в ответ получает любой html файл
// 4) *Сделать работу многопоточной

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import static java.net.http.HttpRequest.BodyPublishers.ofFile;

public class HttpClientRunner {
  static void main() throws IOException, InterruptedException {
    var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    var request =
        HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8082"))
            .header("content-type", "application/json")
            .POST(ofFile(Path.of("src/main/resources/request.json")))
            .build();

    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.headers());
    System.out.println(response.body());
  }
}
