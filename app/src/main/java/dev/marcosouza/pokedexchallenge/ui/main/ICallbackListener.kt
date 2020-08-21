package dev.marcosouza.pokedexchallenge.ui.main

import dev.marcosouza.pokedexchallenge.model.Pokemon

interface ICallbackListener {

    fun onCallBackLaunchDetails(pokemon: Pokemon)
}