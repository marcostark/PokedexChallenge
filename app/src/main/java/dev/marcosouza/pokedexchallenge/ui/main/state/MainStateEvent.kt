package dev.marcosouza.pokedexchallenge.ui.main.state

sealed class MainStateEvent {

    class GetPokemonsEvent: MainStateEvent()

    class None: MainStateEvent()

}