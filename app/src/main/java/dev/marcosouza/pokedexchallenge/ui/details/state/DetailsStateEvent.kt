package dev.marcosouza.pokedexchallenge.ui.details.state

sealed class DetailsStateEvent {

    class GetPokemonDetails: DetailsStateEvent()

    class GetPokemonEvolutions: DetailsStateEvent()

    class GetPokemonAbilities: DetailsStateEvent()

    class None: DetailsStateEvent()

}