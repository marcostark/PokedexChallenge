package dev.marcosouza.pokedexchallenge.ui.main.state

sealed class MainStateEvent {

    class GetPokemonsEvent: MainStateEvent()

    class GetPokemonDetails: MainStateEvent()

    class GetSearchPokemon: MainStateEvent()

    class None: MainStateEvent()

}