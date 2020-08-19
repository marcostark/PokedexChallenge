package dev.marcosouza.pokedexchallenge.ui.main.state

import dev.marcosouza.pokedexchallenge.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}