package com.github.perschola;

import java.sql.*;
import java.util.List;
import java.util.StringJoiner;

public class PokemonDAOImpl implements PokemonDAO {
    MyObject connectorObj = new MyObject();
    Connection connection = connectorObj.getConnection("mysql");

    public void createPokemon() {
        connectorObj.registerJDBCDriver();

        connectorObj.executeStatement(connection, "CREATE DATABASE IF NOT EXISTS databaseName;");
        connectorObj.executeStatement(connection, "USE databaseName;");
        connectorObj.executeStatement(connection, new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS databaseName.pokemonTable(")
                .append("id int auto_increment primary key,")
                .append("name text not null,")
                .append("primary_type int not null,")
                .append("secondary_type int null);").toString());

    }


    @Override
    public boolean addPokemon(List<Pokemon> pokemonList) {

        try {
            for (Pokemon pokemon : pokemonList) {

                PreparedStatement ps = connection.prepareStatement("INSERT INTO databaseName.pokemonTable (name,primary_type,secondary_type) VALUES ( ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, pokemon.getName());
                ps.setInt(2, pokemon.getPrimaryType());
                ps.setInt(3, pokemon.getSecondaryType());
                int numberOfRowsAffected = ps.executeUpdate();

                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    pokemon.setId(resultSet.getInt(1));
                }


                if (numberOfRowsAffected == 1) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
}

    @Override
    public boolean removePokemon(Pokemon pokemon) {
        return false;
    }

    public void viewAllPokemon() {
        String query = "SELECT * FROM databaseName.pokemonTable;";
        ResultSet resultSet = connectorObj.executeQuery(connection, query);
        printResults(resultSet);

    }


    @Override
    public boolean updatePokemon(Pokemon pokemon) {
        return false;
    }

    @Override
    public Pokemon getPokemon() {
        return null;
    }


    public void printResults(ResultSet resultSet) {
        try {
            for (Integer rowNumber = 1; resultSet.next(); rowNumber++) {
                String firstColumnData = resultSet.getString(1);
                String secondColumnData = resultSet.getString(2);
                String thirdColumnData = resultSet.getString(3);
                String fourthColumnData = resultSet.getString(4);
                System.out.println(new StringJoiner("\n")
                        .add("Row number = " + rowNumber.toString())
                        .add("First Column = " + firstColumnData)
                        .add("Second Column = " + secondColumnData)
                        .add("Third column = " + thirdColumnData)
                        .add("Fourth column = " + fourthColumnData)
                        .toString());
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
}
