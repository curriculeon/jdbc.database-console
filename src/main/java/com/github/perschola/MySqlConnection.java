package com.github.perschola;

import com.github.perschola.utils.IOConsole;

import java.sql.*;

import java.util.StringJoiner;

public class MySqlConnection implements Runnable {
    IOConsole console = new IOConsole();
    public void run() {
        registerJDBCDriver();
        Connection mysqlDbConnection = getConnection("mariadb");

        executeStatement(mysqlDbConnection, "CREATE DATABASE IF NOT EXISTS userdatabase;");
        executeStatement(mysqlDbConnection, "USE userdatabase;");
        executeStatement(mysqlDbConnection, new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS userdatabase.pokemonTable(")
                .append("id int auto_increment primary key,")
                .append("name text not null,")
                .append("primary_type int not null,")
                .append("secondary_type int null);").toString());

        String userInput = "";
        do{
              userInput = console.getStringInput("Select from the following options: " +
                      "\n\t [add-entity], [remove-entity], [get-entity], [update-entity] [quit]");
              if(userInput.equalsIgnoreCase("add-entity"))
                  addEntity(mysqlDbConnection);
              else if(userInput.equalsIgnoreCase("remove-entity"))
                  removeEntity(mysqlDbConnection);
              else if(userInput.equalsIgnoreCase("get-entity"))
                  getEntity(mysqlDbConnection);
              else if(userInput.equalsIgnoreCase("update-entity"))
                  updateEntity(mysqlDbConnection);
        }while(!userInput.equalsIgnoreCase("quit"));
    }

    void registerJDBCDriver() {
        // Attempt to register JDBC Driver
        try {
            Class.forName(Driver.class.getName());
        } catch ( ClassNotFoundException e1) {
            throw new Error(e1);
        }
    }

    public Connection getConnection(String dbVendor) {
        String username = "admin";
        String password = "password";
        String url = "jdbc:" + dbVendor + "://localhost:3306/userdatabase?";
        try {
            return DriverManager.getConnection(url,username,password);
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

    public void printResults(ResultSet resultSet)  {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next())
            for(int i = 1; i <= metaData.getColumnCount();i ++)
                console.println(metaData.getColumnName(i) + ": " + resultSet.getString(i) );
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

    void getEntity(Connection mysqlDbConnection){
        String table = console.getStringInput("Enter table");
        String entity = console.getStringInput("Enter entity to get from table");
        String query = "SELECT " + entity + " FROM userdatabase." +table +";";
        ResultSet resultSet = executeQuery(mysqlDbConnection, query);
        printResults(resultSet);
    }

    void addEntity(Connection mysqlDbConnection){
        String table = console.getStringInput("Enter table");
        Integer id; String name; Integer primary_type; Integer secondary_type;
        id = console.getIntegerInput("Enter id");
        name = console.getStringInput("Enter pokemon name");
        primary_type = console.getIntegerInput("Enter Primary Type");
        secondary_type = console.getIntegerInput("Enter Secondary Type");
        executeStatement(mysqlDbConnection, "INSERT INTO userdatabase." + table + " " +
                "(id, name, primary_type, secondary_type)" +
                " VALUES (" + id + ", '" + name + "', " + primary_type + ", " + secondary_type + ")");

    }

    void removeEntity(Connection mysqlDbConnection){
        String table = console.getStringInput("Enter table");
        String id = console.getStringInput("Enter id of entity to remove");
        executeStatement(mysqlDbConnection, "DELETE FROM " + table + " WHERE id = " + id + ";");
    }

    void updateEntity(Connection mysqlDbConnection){
        String table = console.getStringInput("Enter table");
        String id = console.getStringInput("Enter id of entity to update");
        String property = console.getStringInput("Enter property to update");
        String newValue = console.getStringInput("Enter new value");
        executeStatement(mysqlDbConnection, "UPDATE " + table + " SET " + property + " = '" + newValue + "' WHERE " + "id = " + id + ";");
    }



}
