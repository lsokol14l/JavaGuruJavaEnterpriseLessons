package by.michael;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
  private final UserService userService = new UserService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    var user = userService.getUser(1L);

    resp.setContentType("text/html");

    var writer = resp.getWriter();

    writer.write("<html><body>");
    writer.write("<h1>Hello, " + user.get().getName() + "!</h1>");
    writer.write("</body></html>");
    writer.close();
  }
}
