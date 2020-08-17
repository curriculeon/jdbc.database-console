package com.github.perschola;

import java.sql.*;

import com.mysql.cj.jdbc.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MyObject implements Runnable {
    Scanner scanner = new Scanner(System.in);
    List<Pokemon> pokemonList = new ArrayList<>();
    //PokemonDAOImpl pokemonDAO = new PokemonDAOImpl();

    public void run() {
        promptUser();

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
        String password = "dbpassword";
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

    public void promptUser() {
        String option;
        do {
            System.out.println("Do you want to add/remove/update/get/quit Pokemon details?");
            option= scanner.nextLine();
            switch (option) {
                case "add":
                    insert();
                    break;
                case "remove":
                    removePokemonByName();
                    break;
                case "update":
                    updatePokemonById();
                    break;
                case "get":
                    getPokemonDetails();
                    break;
                case "quit": break;
            }
        }while(!("quit".equals(option)));

    }

    private void getPokemonDetails() {
        System.out.println("Enter name of the pokemon character to be viewed");
        String pokemonName = scanner.nextLine();
        //TODO
    }

    private void updatePokemonById() {
        System.out.println("Enter Id of the pokemon character to be updated");
        Integer pokemonName = scanner.nextInt();
        //TODO
    }

    private void removePokemonByName() {
        System.out.println("Enter name of the pokemon character to be removed");
        String pokemonName = scanner.nextLine();
        //TODO
    }

    private void insert() {
        System.out.println("Enter name of the pokemon character");
        String pokemonName = scanner.nextLine();
        System.out.println("Enter primary type of the pokemon character");
        Integer pokemonPrimaryType = Integer.valueOf(scanner.nextLine());
        System.out.println("Enter primary type of the pokemon character");
        Integer pokemonSecondaryType = Integer.valueOf(scanner.nextLine());

        Pokemon pokemon = new Pokemon(pokemonName, pokemonPrimaryType, pokemonSecondaryType);
        pokemonList.add(pokemon);
        boolean success =  new PokemonDAOImpl().addPokemon(pokemonList);
        if (success) {
            System.out.println("Pokemon stored successfully");
        }

    }


}
