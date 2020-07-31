package com.github.perschola;

import java.sql.*;
import com.mysql.cj.jdbc.Driver;
import java.util.StringJoiner;

public class MyObject implements Runnable {
    public void run() {
        registerJDBCDriver();
        Connection mysqlDbConnection = getConnection("mysql");

        executeStatement(mysqlDbConnection, "DROP DATABASE IF EXISTS databaseName;");
        executeStatement(mysqlDbConnection, "CREATE DATABASE IF NOT EXISTS databaseName;");
        executeStatement(mysqlDbConnection, "USE databaseName;");
        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS databaseName.pokemonTable(")
                .append("id int auto_increment primary key,")
                .append("name text not null,")
                .append("primary_type int not null,")
                .append("secondary_type int null);").toString());

        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("INSERT INTO databaseName.pokemonTable ")
                .append("(id, name, primary_type, secondary_type)")
                .append(" VALUES (12, 'Ivysaur', 3, 7);").toString());

        String query = "SELECT * FROM databaseName.pokemonTable;";
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
        String url = "jdbc:" + dbVendor + "://127.0.0.1/";
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
                String firstColumnData = resultSet.getString(1);
                String secondColumnData = resultSet.getString(2);
                String thirdColumnData = resultSet.getString(3);
                System.out.println(new StringJoiner("\n")
                        .add("Row number = " + rowNumber.toString())
                        .add("First Column = " + firstColumnData)
                        .add("Second Column = " + secondColumnData)
                        .add("Third column = " + thirdColumnData)
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
