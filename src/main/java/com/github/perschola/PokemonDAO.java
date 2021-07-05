package com.github.perschola;

import java.util.List;

public interface PokemonDAO {
    public void createPokemon();
    public boolean addPokemon(List<Pokemon> pokemon);
    public boolean removePokemon(String pokemon);
    public boolean updatePokemon(Integer pokemonId, String pokemonField,String value);
    public void getPokemon(String pokemonName);


}
