package org.example.jdbc;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.sql.*;
import java.util.List;

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

    /**
     * JDBC 简单增删改查
     */
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

    /**
     * 添加 PreparedStatement
     */
    @Test
    public void prepareInsert() throws SQLException {
        List<User> users = Lists.newArrayList(
                new User("1ZhSan", "A nice one"),
                new User("1LiSi", "Not a pretty girl"),
                new User("WanWu", "what a good boy"));
        Connection connection = Utils.getConnection();
        PreparedStatement pst = connection.prepareStatement("insert into t_user (username, password) values (?, ?)");

        connection.setAutoCommit(false);

        try {
            users.forEach(user -> {
                try {
                    pst.setString(1, user.getUsername());
                    pst.setString(2, user.getPassword());
                    pst.addBatch();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            });
            pst.executeBatch();
            connection.commit();
            System.out.println("Commit");
        } catch (SQLException e) {
            System.out.println("异常 " + e.getMessage() + ", rollback");
            connection.rollback();
        }
        selectAll(connection);
    }

    private void selectAll(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select username, password from t_user");
        ResultSet resultSet = statement.executeQuery();
        int col = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
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
