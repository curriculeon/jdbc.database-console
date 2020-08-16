package com.github.perschola;

import java.sql.*;
import java.util.StringJoiner;

public class MyObject implements Runnable {
    IOConsole console = new IOConsole();
    Connection mysqlDbConnection = getConnection("mariadb");
    public void run() {
        registerJDBCDriver();


        executeStatement("DROP DATABASE IF EXISTS databaseName;");
        executeStatement("CREATE DATABASE IF NOT EXISTS databaseName;");
        executeStatement("USE databaseName;");
        executeStatement(new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS databaseName.pokemonTable(")
                .append("id int auto_increment primary key,")
                .append("name text not null,")
                .append("primary_type int not null,")
                .append("secondary_type int null);").toString());

        executeStatement(new StringBuilder()
                .append("INSERT INTO databaseName.pokemonTable ")
                .append("(id, name, primary_type, secondary_type)")
                .append(" VALUES (12, 'Ivysaur', 3, 7);").toString());

        String query = "SELECT * FROM databaseName.pokemonTable;";
        ResultSet resultSet = executeQuery(query);
        printResults(resultSet);
        addEntityToTable();
        getEntityField();
    }

    void registerJDBCDriver() {
        // Attempt to register JDBC Driver
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            throw new Error(e1);
        }
    }

    public Connection getConnection(String dbVendor) {
        String username = "root";
        String password = "muchEO21";
        String url = "jdbc:" + dbVendor + "://localhost:3300/";
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

    void executeStatement(String sqlStatement) {
        try {
            Statement statement = getScrollableStatement(mysqlDbConnection);
            statement.execute(sqlStatement);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    ResultSet executeQuery(String sqlQuery) {
        try {
            Statement statement = getScrollableStatement(mysqlDbConnection);
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public void  addEntityToTable(){
        String tableName, id, name, primary_type, secondary_type;
        tableName = console.getStringInput("Select table");
        id = console.getStringInput("Enter userId");
        name = console.getStringInput("Enter user name");
        primary_type = console.getStringInput("Enter a primary number");
        secondary_type = console.getStringInput("Enter a secondary number");
        String sqlQuery = "INSERT INTO pokemontable (id, name, primary_type, secondary_type) Values (" + id + ",'" + name + "'," + primary_type
                + "," + secondary_type + ") ";
        executeStatement(sqlQuery);
    }

    public void removeEntityFromTable(){
        String tableName, id;
        tableName = console.getStringInput("Enter table to delete user");
        id = console.getStringInput(" Enter the id of a user to remove user");
        String sqlQuery = "DELETE FROM " + tableName + " WHERE  id = " + id;
        executeStatement(sqlQuery);
    }

    public void updateEntityTable(){
        String id, primary_type, tableName;
        tableName = console.getStringInput("Enter table to delete user");
        id = console.getStringInput(" Enter user id to update primary type");
        primary_type = console.getStringInput("Enter a new primary type number");
        String sqlQuery = "UPDATE " + tableName + "SET " + "primary_type = " + primary_type +" WHERE  id = " + id;
        executeStatement(sqlQuery);
    }

    public void getEntityField(){
        String tableName, id;
        tableName = console.getStringInput("Enter table:");
        id = console.getStringInput("Enter user id " );
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE id = " + id + ";";
        ResultSet resultSet = executeQuery(sqlQuery);
        printResults(resultSet);


    }

}
