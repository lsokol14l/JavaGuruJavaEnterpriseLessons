package by.michael.jdbc.servlet;

import by.michael.jdbc.dto.FlightDto;
import by.michael.jdbc.service.FlightService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {
  private final FlightService flightService = FlightService.getInstance();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html");
    resp.setCharacterEncoding(StandardCharsets.UTF_8);

    try (var writer = resp.getWriter()) {
      writer.write("<h1>Список перелетов</h1>");
      writer.write("<ul>");
      List<FlightDto> all = flightService.findAll();

      for (FlightDto flightDto : all) {
        writer.write(
"""
  <li>
    <a href="tickets?flightId=%d">%d: %s</a>
  </li>
"""
                .formatted(flightDto.id(), flightDto.id(), flightDto.description()));
      }

      writer.write("</ul>");
    }
  }
}
