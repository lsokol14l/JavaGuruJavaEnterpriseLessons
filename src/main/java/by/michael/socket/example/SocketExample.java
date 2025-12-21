package by.michael.socket.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketExample {
  public static void main(String[] args) {
    try (Socket socket = new Socket("google.com", 80);
        var inputStream = new DataInputStream(socket.getInputStream());
        var outputStream = new DataOutputStream(socket.getOutputStream()); ) {

      outputStream.writeUTF("Hello from client");
      byte[] response = inputStream.readAllBytes();

      System.out.println(response.length);
      System.out.println(new String(response));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
