package rafael.alves.junitandrxmvp.repository.listeners;

import rafael.alves.junitandrxmvp.model.PokemonList;

public interface PokemonListListener extends BaseListener {

    /**
     * PokemonList done loading
     *
     * @param pokemonList - PokemonList model
     */
    void onPokemonListLoad(PokemonList pokemonList);
}
