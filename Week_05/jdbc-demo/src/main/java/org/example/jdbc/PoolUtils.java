package org.example.jdbc;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PoolUtils {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static HikariDataSource dataSource = null;

    static {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setConnectionTimeout(3000L);
        dataSource.setIdleTimeout(60000L);
        dataSource.setMinimumIdle(10);
        dataSource.setMaximumPoolSize(60);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
