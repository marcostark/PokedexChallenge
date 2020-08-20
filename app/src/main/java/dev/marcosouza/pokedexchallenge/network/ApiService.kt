package dev.marcosouza.pokedexchallenge.network

import androidx.lifecycle.LiveData
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import dev.marcosouza.pokedexchallenge.util.Constants
import dev.marcosouza.pokedexchallenge.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    fun getAllPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : LiveData<GenericApiResponse<PokemonResponse>>
}