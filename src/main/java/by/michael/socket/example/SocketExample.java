package by.michael.socket.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketExample {
  public static void main(String[] args) {
    try (Socket socket = new Socket("google.com", 80);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

      String request =
          """
                      GET / HTTP/1.1\r
                      Host: www.google.com\r
                      Connection: close\r
                      User-Agent: JavaSocketExample/1.0\r
                      \r
                      """;

      out.write(request.getBytes(StandardCharsets.UTF_8));
      out.flush();

      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
