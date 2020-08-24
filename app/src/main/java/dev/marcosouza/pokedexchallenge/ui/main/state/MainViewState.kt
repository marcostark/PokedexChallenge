package dev.marcosouza.pokedexchallenge.ui.main.state

import dev.marcosouza.pokedexchallenge.model.PokemonAbility
import dev.marcosouza.pokedexchallenge.model.PokemonDetails
import dev.marcosouza.pokedexchallenge.model.PokemonResponse

data class MainViewState (

    var pokemons: PokemonResponse? = null,

    var pokemon: PokemonDetails? = null,

    var abilities: PokemonAbility? = null

)