package com.github.perschola;

import java.sql.*;

import com.mysql.cj.jdbc.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MyObject implements Runnable {
    Scanner scanner = new Scanner(System.in);
    List<Pokemon> pokemonList = new ArrayList<>();


    public void run() {
        PokemonDAOImpl pokemonDAO = new PokemonDAOImpl();
        promptUser(pokemonDAO);

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

    public void promptUser(PokemonDAOImpl pokemonDAO) {
        String option;
        do {
            System.out.println("Do you want to add/remove/update/get/view-all/quit Pokemon details?");
            option = scanner.nextLine();
            switch (option) {
                case "add":
                    insert(pokemonDAO);
                    break;
                case "remove":
                    removePokemonByName(pokemonDAO);
                    break;
                case "update":
                    updatePokemonById(pokemonDAO);
                    break;
                case "get":
                    getPokemonDetails(pokemonDAO);
                    break;
                case "view-all":
                    viewAllPokemonDetails(pokemonDAO);
                    break;
                case "quit":
                    break;
            }
        } while (!("quit".equals(option)));

    }

    private void viewAllPokemonDetails(PokemonDAOImpl pokemonDAO) {
        try {
            pokemonDAO.viewAllPokemon();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void updatePokemonById(PokemonDAOImpl pokemonDAO) {
        boolean success;
        viewAllPokemonDetails(pokemonDAO);
        System.out.println("Enter id of the pokemon character to be updated");
        Integer pokemonId = scanner.nextInt();
        System.out.println("select below options to change in pokemon character to be updated");
        System.out.println("[name,primary-type,secondary-type]");
        String pokemonField = scanner.nextLine();
        if("name".equals(pokemonField)) {
            System.out.println("Enter new name of the pokemon character to be updated");
            String newName=scanner.nextLine();
            success=pokemonDAO.updatePokemon(pokemonId, pokemonField,newName);
        }else if("primary-type".equals(pokemonField)) {
            System.out.println("Enter new primary-type of the pokemon character to be updated");
            String newPrimaryType=scanner.nextLine();
            success = pokemonDAO.updatePokemon(pokemonId, pokemonField,newPrimaryType);
        }else {
            pokemonField="secondary-type";
            System.out.println("Enter new secondary-type of the pokemon character to be updated");
            String newSecondaryType=scanner.nextLine();
            success = pokemonDAO.updatePokemon(pokemonId, pokemonField,newSecondaryType);
        }
        if (success) {
            System.out.println("Pokemon updated successfully");
        }

    }


    private void getPokemonDetails(PokemonDAOImpl pokemonDAO) {
        System.out.println("Enter name of the pokemon character to be viewed");
        String pokemonName = scanner.nextLine();
        pokemonDAO.getPokemon(pokemonName);
    }


    private void removePokemonByName(PokemonDAOImpl pokemonDAO) {
        System.out.println("Enter name of the pokemon character to be removed");
        String pokemonName = scanner.nextLine();
        boolean success=pokemonDAO.removePokemon(pokemonName);
        if (success) {
            System.out.println("Pokemon removed successfully");
        }
    }

    private void insert(PokemonDAOImpl pokemonDAO) {
        System.out.println("Enter name of the pokemon character");
        String pokemonName = scanner.nextLine();
        System.out.println("Enter primary type of the pokemon character");
        Integer pokemonPrimaryType = Integer.valueOf(scanner.nextLine());
        System.out.println("Enter secondary type of the pokemon character");
        Integer pokemonSecondaryType = Integer.valueOf(scanner.nextLine());

        Pokemon pokemon = new Pokemon(pokemonName, pokemonPrimaryType, pokemonSecondaryType);
        pokemonList.add(pokemon);
        boolean success = pokemonDAO.addPokemon(pokemonList);
        if (success) {
            System.out.println("Pokemon stored successfully");
        }

    }


}
