package by.michael.servletapplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/map")
public class SecondMapServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html");

    PrintWriter writer = resp.getWriter();

    Map<String, String[]> parameterMap = req.getParameterMap();

    parameterMap
            .forEach((key, value) -> writer.println("<h3>" + key + " = " + Arrays.toString(value) + "</h3>"));
  }
}
