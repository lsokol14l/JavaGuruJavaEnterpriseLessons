package by.michael.servletapplication;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/pidmax")
public class MyFirstServlet extends HttpServlet {
  String msg;

  @Override
  public void init(ServletConfig config) throws ServletException {
    msg = "Заебала эта дура, вызвал ей такси до дома.";
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html");
    resp.setHeader("My-Header", "PIDMAX");

    String header = req.getHeader("user-agent");

    PrintWriter writer = resp.getWriter();
    writer.println("<html><h1>" + msg + "</h1>");
    writer.println("<h2>" + header + "</h2>");

    Enumeration<String> headerNames = req.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String string = headerNames.nextElement();
      writer.println("<h3>" + string + ": " + req.getHeader(string) + "</h3>");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html");

    PrintWriter writer = resp.getWriter();

    writer.println("<h2>" + req.getParameter("login") + "</h2>");
    writer.println("<h2>" + req.getParameter("password") + "</h2>");
  }

  @Override
  public void destroy() {
    System.out.println("Сакни броу");
  }
}
