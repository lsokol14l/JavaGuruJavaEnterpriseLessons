package by.michael.jdbc.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
  private static final String URL_KEY = "db.url";
  private static final String USER_KEY = "db.user";
  private static final String PWD_KEY = "db.pwd";
  private static final String POOL_SIZE_KEY = "db.pool.size";
  private static final int DEFAULT_POOL_SIZE = 10;

  private static BlockingQueue<Connection> pool;

  static {
    initConnectionPool();
  }

  private static void initConnectionPool() {

    String poolSize = PropertiesUtil.getProperty(POOL_SIZE_KEY);
    int size = (poolSize == null || poolSize.isEmpty()) ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
    pool = new ArrayBlockingQueue<>(size);

    for (int i = 0; i < size; i++) {
      var connection = open();
      var proxyConnection = (Connection) Proxy.newProxyInstance(
          ConnectionManager.class.getClassLoader(),
          new Class[] { Connection.class },
          (proxy, method, args) -> method.getName().equals("close")
              ? pool.add((Connection) proxy)
              : method.invoke(connection, args));

      pool.add(proxyConnection);
    }
  }

  private ConnectionManager() {
  }

  public static Connection get() {
    try {
      return pool.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private static Connection open() {
    try {
      return DriverManager.getConnection(
          PropertiesUtil.getProperty(URL_KEY),
          PropertiesUtil.getProperty(USER_KEY),
          PropertiesUtil.getProperty(PWD_KEY));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}