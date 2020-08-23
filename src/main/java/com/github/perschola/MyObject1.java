package com.github.perschola;

import java.sql.*;
import com.mysql.cj.jdbc.Driver;
import java.util.StringJoiner;

public class MyObject1 implements Runnable {
    public void run() {
        registerJDBCDriver();
        Connection mysqlDbConnection = getConnection("mysql");

        executeStatement(mysqlDbConnection, "DROP DATABASE IF EXISTS databaseName;");
        executeStatement(mysqlDbConnection, "CREATE DATABASE IF NOT EXISTS databaseName;");
        executeStatement(mysqlDbConnection, "USE databaseName;");
        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS databaseName.accounts(")
                .append("accountNumber int primary key,")
                .append("customerName text not null,")
                .append("email text not null,")
                .append("address text not null,")
                .append("phoneNumber int);").toString());

        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("INSERT INTO databaseName.accounts")
                .append("(accountNumber, customerName, email, address, phoneNumber)")
                .append(" VALUES (111, 'monica', 'monica@gmail.com', '1 happy ln, HS 23059', 1234567890);").toString());

        String query = "SELECT * FROM databaseName.accounts;";
        ResultSet resultSet = executeQuery(mysqlDbConnection, query);
        printResults(resultSet);
    }

    void registerJDBCDriver() {
        // Attempt to register JDBC Driver
        try {
            DriverManager.registerDriver(Driver.class.newInstance());
        } catch (InstantiationException | IllegalAccessException | SQLException e1) {
            throw new Error(e1);
        }
    }

    public Connection getConnection(String dbVendor) {
        String username = "root";
        String password = "";
        String url = "jdbc:" + dbVendor + "://localhost:3300";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public Statement getScrollableStatement(Connection connection) {
        int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
        int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
        try {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public void printResults(ResultSet resultSet) {
        try {
            for (Integer rowNumber = 0; resultSet.next(); rowNumber++) {
                String accountNumber = resultSet.getString(1);
                String customerName = resultSet.getString(2);
                String email = resultSet.getString(3);
                String address = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                System.out.println(new StringJoiner("\n")
                        .add("Row number = " + rowNumber.toString())
                        .add("accountNumber = " + accountNumber)
                        .add("customerName = " + customerName)
                        .add("email = " + email)
                        .add("address = " + address)
                        .add("phoneNumber = " + phoneNumber)
                        .toString());
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    void executeStatement(Connection connection, String sqlStatement) {
        try {
            Statement statement = getScrollableStatement(connection);
            statement.execute(sqlStatement);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    ResultSet executeQuery(Connection connection, String sqlQuery) {
        try {
            Statement statement = getScrollableStatement(connection);
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

}