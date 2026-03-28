package by.michael.jdbc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConnectionManagerTests {
  private static final String URL_KEY = "db.url";
  private static final String USER_KEY = "db.user";

  @DisplayName("Testing get connection")
  @Test
  void testGetConnection() {
    Connection connection = ConnectionManager.get();
    assertNotNull(connection);
    try {
      assertEquals(PropertiesUtil.getProperty(URL_KEY), connection.getMetaData().getURL());
      assertEquals(PropertiesUtil.getProperty(USER_KEY), connection.getMetaData().getUserName());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
