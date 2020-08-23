package dev.marcosouza.pokedexchallenge.repository

import androidx.lifecycle.LiveData
import dev.marcosouza.pokedexchallenge.model.*
import dev.marcosouza.pokedexchallenge.network.CustomRetrofitBuilder
import dev.marcosouza.pokedexchallenge.ui.details.state.DetailsViewState
import dev.marcosouza.pokedexchallenge.ui.main.state.MainViewState
import dev.marcosouza.pokedexchallenge.util.ApiSuccessResponse
import dev.marcosouza.pokedexchallenge.util.Constants
import dev.marcosouza.pokedexchallenge.util.DataState
import dev.marcosouza.pokedexchallenge.util.GenericApiResponse

object PokemonRepository {

    fun getAllPokemons(
        page: Int
    ): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<PokemonResponse, MainViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonResponse>) {
                result.value = DataState.data(data = MainViewState(
                        pokemons = response.body
                    ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonResponse>> {
               return CustomRetrofitBuilder.apiService.getAllPokemons(
                   Constants.LIMIT,
                   page * Constants.LIMIT)
            }
        }.asLiveData()
    }

    fun getPokemon(
        name: String
    ): LiveData<DataState<DetailsViewState>> {
        return object: NetworkBoundResource<PokemonDetails, DetailsViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonDetails>) {
                result.value = DataState.data(data = DetailsViewState(
                    pokemon = response.body
                ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonDetails>> {
                return CustomRetrofitBuilder.apiService.getPokemonByName(name)
            }
        }.asLiveData()
    }

    fun getSearchPokemon(
        query: String
    ): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<PokemonDetails, MainViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonDetails>) {
                result.value = DataState.data(data = MainViewState(
                    pokemon = response.body
                ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonDetails>> {
                return CustomRetrofitBuilder.apiService.getPokemonByName(query)
            }
        }.asLiveData()
    }

    fun getEvolutions(
        query: String
    ): LiveData<DataState<DetailsViewState>> {
        return object: NetworkBoundResource<PokemonDetails, DetailsViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonDetails>) {
                result.value = DataState.data(data = DetailsViewState(
                    pokemon = response.body
                ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonDetails>> {
                return CustomRetrofitBuilder.apiService.getPokemonByName(query)
            }
        }.asLiveData()
    }

    fun getAbilities(
        query: String
    ): LiveData<DataState<DetailsViewState>> {
        return object: NetworkBoundResource<PokemonAbility, DetailsViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonAbility>) {
                result.value = DataState.data(data = DetailsViewState(
                    abilities = response.body
                ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonAbility>> {
                return CustomRetrofitBuilder.apiService.getAbility(query)
            }
        }.asLiveData()
    }

    fun getType(
        query: String
    ): LiveData<DataState<DetailsViewState>> {
        return object: NetworkBoundResource<PokemonType, DetailsViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonType>) {
                result.value = DataState.data(data = DetailsViewState(
                    type = response.body
                ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonType>> {
                return CustomRetrofitBuilder.apiService.getType(query)
            }
        }.asLiveData()
    }

}