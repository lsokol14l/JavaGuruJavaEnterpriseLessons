package by.michael.socket.task1easy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
  static void main() {
    try (var socket = new Socket("localhost", 8081);
        var inputStream = new DataInputStream(socket.getInputStream());
        var outputStream = new DataOutputStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in)) {
      String request = sc.nextLine();
      while (!request.equals("exit")) {
        outputStream.writeUTF(request);
        String response = inputStream.readUTF();
        System.out.println("answer from server is: " + response);
        request = sc.nextLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
