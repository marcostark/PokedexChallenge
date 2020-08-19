package dev.marcosouza.pokedexchallenge.network

import androidx.lifecycle.LiveData
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import dev.marcosouza.pokedexchallenge.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("pokemon?offset=0&limit=20")
    fun getAllPokemons() : LiveData<GenericApiResponse<PokemonResponse>>
}