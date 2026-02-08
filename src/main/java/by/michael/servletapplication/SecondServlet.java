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

@WebServlet("/second")
public class SecondServlet extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html");

    PrintWriter writer = resp.getWriter();

    Enumeration<String> parameterNames = req.getParameterNames();

    while (parameterNames.hasMoreElements()) {
      String string = parameterNames.nextElement();
      writer.println("<h3>" + string + ": " + req.getParameter(string) + "</h3>");
    }
  }

  @Override
  public void destroy() {
    super.destroy();
  }
}
