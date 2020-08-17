package com.github.perschola;

import java.util.List;

public interface PokemonDAO {
    public void createPokemon();
    public boolean addPokemon(List<Pokemon> pokemon);
    public boolean removePokemon(Pokemon pokemon);
    public boolean updatePokemon(Pokemon pokemon);
    public Pokemon getPokemon();


}
