package by.michael.socket.task1easy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class SocketServer {
  static void main() {
    try (var serverSocket = new ServerSocket(8081);
        var socket = serverSocket.accept();
        var inputStream = new DataInputStream(socket.getInputStream());
        var outputStream = new DataOutputStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in)) {
      String request = inputStream.readUTF();
      while (!request.equals("exit")) {
        System.out.println("Клиент отправил запрос: " + request);
        outputStream.writeUTF(sc.nextLine());
        request = inputStream.readUTF();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
