// java
package by.michael;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetworkInfo {
  public static void main(String[] args) throws Exception {
    // TCP: создаём реальное соединение и читаем локальный адрес/порт
    try (Socket socket = new Socket("google.com", 80)) {
      System.out.println(
          "TCP local: " + socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort());
      System.out.println(
          "TCP remote: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
    }

    // UDP: можно 'connect' к публичному IP без отправки пакетов — покажет локальный интерфейс/порт
    try (DatagramSocket ds = new DatagramSocket()) {
      ds.connect(InetAddress.getByName("8.8.8.8"), 53);
      System.out.println(
          "UDP local: " + ds.getLocalAddress().getHostAddress() + ":" + ds.getLocalPort());
    }

    // Публичный IP: внешний сервис (например, api.ipify.org)
    var client = HttpClient.newHttpClient();
    var req = HttpRequest.newBuilder(URI.create("https://api.ipify.org")).GET().build();
    var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
    System.out.println("Public IP: " + resp.body());

    // Примечание: если вы за NAT, локальный адрес/порт отличаются от публичного IP/порта.
    // Ответные пакеты приходят по публичному IP:порт и маршрутизируются вашим роутером по
    // NAT-таблице.
  }
}
