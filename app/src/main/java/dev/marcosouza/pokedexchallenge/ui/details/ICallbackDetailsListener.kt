package dev.marcosouza.pokedexchallenge.ui.details

import dev.marcosouza.pokedexchallenge.model.Ability
import dev.marcosouza.pokedexchallenge.model.PokemonAbility

interface ICallbackDetailsListener {

    fun onCallAbilityDetails(ability: Ability)

    fun onCallDialogAbilityDetails(ability: PokemonAbility)
}