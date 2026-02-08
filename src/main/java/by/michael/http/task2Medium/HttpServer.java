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
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
  private final int port;
  private final ExecutorService pool;

  public HttpServer(int port, int nThreads) {
    this.port = port;
    pool = Executors.newFixedThreadPool(nThreads);
  }

  public void run() {
    try {
      var serverSocket = new ServerSocket(port);
      while (true) {
        var socket = serverSocket.accept();
        System.out.println("SocketAccepted");
        pool.submit(() -> processSocket(socket));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void processSocket(Socket socket) {
    try (socket;
        BufferedReader inputStream =
            new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var outputStream = new DataOutputStream(socket.getOutputStream())) {
      // 1) Сначала обработаем запрос клиента
      // чуть поспим типо процесс долгий обрабатывается
      Thread.sleep(1000);

      // 1.1 прочитаем headers (заголовки запроса)
      List<String> headers = new ArrayList<>();
      String header;
      do {
        header = inputStream.readLine();
        headers.add(header);
        System.out.println(header);
      } while (!header.isEmpty());

      // 1.2 ищем длину body
      int bodyLength =
          headers.stream()
              .filter(item -> item.toLowerCase().startsWith("content-length:"))
              .map(item -> item.split(":", 2)[1].trim())
              .mapToInt(Integer::parseInt)
              .findFirst()
              .orElse(0);

      if (bodyLength > 0) {
        byte[] body = socket.getInputStream().readNBytes(bodyLength);
        System.out.println(new String(body));
      }

      // 2) теперь нужно ответить ему
      byte[] body = Files.readAllBytes(Path.of("src/main/resources/DavidGoggins.html"));

      outputStream.write(
          """
              HTTP/1.1 200 OK
              content-type: text/html
              content-length: %s
              """
              .formatted(body.length)
              .getBytes());
      // перевод на новую строку
      outputStream.write(System.lineSeparator().getBytes());
      outputStream.write(body);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
