package by.michael.http.task2Medium;

// # Задание 4*
//
// 1) Написать HTTP Server
// 2) Написать HTTP Client
// 3) Клиент отправляет json файл и в ответ получает любой html файл
// 4) *Сделать работу многопоточной

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {
  private final int port;

  public HttpServer(int port) {
    this.port = port;
  }

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept()) {
      processSocket(socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void processSocket(Socket socket) throws IOException {
    try (var input = new DataInputStream(socket.getInputStream());
        var output = new DataOutputStream(socket.getOutputStream())) {

      System.out.println(new String(input.readNBytes(4000)));

      byte[] body = Files.readAllBytes(Path.of("src/main/resources/DavidGoggins.html"));

      output.write(
          """
                 HTTP/1.1 200 OK
                 content-type: text/html
                 content-length: %s
                 """
              .formatted(body.length)
              .getBytes());
      // перевод на новую строку
      output.write(System.lineSeparator().getBytes());

      output.write(body);
    }
  }
}
