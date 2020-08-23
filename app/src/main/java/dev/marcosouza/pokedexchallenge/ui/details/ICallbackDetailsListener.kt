package dev.marcosouza.pokedexchallenge.ui.details

import dev.marcosouza.pokedexchallenge.model.Ability
import dev.marcosouza.pokedexchallenge.model.PokemonAbility
import dev.marcosouza.pokedexchallenge.model.PokemonType
import dev.marcosouza.pokedexchallenge.model.Type

interface ICallbackDetailsListener {

    fun onCallAbilityDetails(ability: Ability)

    fun onCallDialogAbilityDetails(ability: PokemonAbility)

    fun onCallPokemonsByType(type: Type)
}