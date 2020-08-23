package dev.marcosouza.pokedexchallenge.ui.details.state

import dev.marcosouza.pokedexchallenge.model.PokemonAbility
import dev.marcosouza.pokedexchallenge.model.PokemonDetails

data class DetailsViewState (

    var pokemon: PokemonDetails? = null,

    var abilities: PokemonAbility? = null

)