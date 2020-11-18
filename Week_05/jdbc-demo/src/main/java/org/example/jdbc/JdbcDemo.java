package org.example.jdbc;

import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo {

    @Test
    public void createTable() throws SQLException {
        Connection connection = Utils.getConnection();
        String tableSql = "create table if not exists t_user (username varchar(50) not null primary key,"
                + "password varchar(20) not null);";

        Statement stmt = connection.createStatement();
        stmt.execute(tableSql);
        System.out.println("Create table success");
    }

    @Test
    public void insertUpdateDelete() throws SQLException {
        Connection connection = Utils.getConnection();

        String username = "heix";
        String passwordOne = "ha1!2222";
        String passwordTwo = "09010101";

        // delete
        delete(connection, username);

        // insert
        String insertSql = "insert into t_user (username, password) values ('" + username + "', '" + passwordOne + "')";
        Statement statement = connection.createStatement();
        statement.execute(insertSql);
        System.out.println("Insert success");

        // Select
        select(connection, username);

        // update
        String updateSql = "update t_user set password ='" + passwordTwo + "' where username = '" + username + "'";
        statement.execute(updateSql);
        System.out.println("Update success");

        // Select
        select(connection, username);
    }

    private void select(Connection connection, String username) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select username, password from t_user where username = '" + username + "'");
        int col = resultSet.getMetaData().getColumnCount();
        System.out.print("Select Result: " + "\t");
        while (resultSet.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
        }
        System.out.println();
    }

    private void delete(Connection connection, String username) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("delete from t_user where username = '" + username + "'");
    }

}
