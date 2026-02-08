package by.michael.http.task2Medium;

public class HttpServerRunner {
  static void main() {
    HttpServer httpServer = new HttpServer(8082, 10);
    httpServer.run();
  }
}
