package dev.marcosouza.pokedexchallenge.api

import androidx.lifecycle.LiveData
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/pokemon")
    fun getAllPokemons() : LiveData<GenericApiResponse<Pokemon>>
}