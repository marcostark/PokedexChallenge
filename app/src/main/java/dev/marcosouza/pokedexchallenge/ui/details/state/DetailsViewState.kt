package dev.marcosouza.pokedexchallenge.ui.details.state

import dev.marcosouza.pokedexchallenge.model.PokemonAbility
import dev.marcosouza.pokedexchallenge.model.PokemonDetails
import dev.marcosouza.pokedexchallenge.model.PokemonType

data class DetailsViewState (

    var pokemon: PokemonDetails? = null,

    var abilities: PokemonAbility? = null,

    var type: PokemonType?= null

)