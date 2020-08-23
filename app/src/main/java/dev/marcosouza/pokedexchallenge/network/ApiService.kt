package dev.marcosouza.pokedexchallenge.network

import androidx.lifecycle.LiveData
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonAbility
import dev.marcosouza.pokedexchallenge.model.PokemonDetails
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import dev.marcosouza.pokedexchallenge.util.Constants
import dev.marcosouza.pokedexchallenge.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    fun getAllPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : LiveData<GenericApiResponse<PokemonResponse>>

    @GET("pokemon/{name}")
    fun getPokemonByName(
        @Path("name") name: String
    ) : LiveData<GenericApiResponse<PokemonDetails>>

    @GET("pokemon/{id}")
    fun getPokemonById(
        @Path("id") id: Int
    ) : LiveData<GenericApiResponse<PokemonDetails>>

    @GET("evolution-chain/{id}")
    fun getEvolutions(
        @Path("id") id: Int
    ) : LiveData<GenericApiResponse<PokemonDetails>>

    @GET("ability/{query}")
    fun getAbility(
        @Path("query") query: String
    ) : LiveData<GenericApiResponse<PokemonAbility>>
}