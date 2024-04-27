package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static final String HOST = System.getenv("DB_HOST");
    private static final String PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String USERNAME = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%s/%s", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return connection;
    }
}
