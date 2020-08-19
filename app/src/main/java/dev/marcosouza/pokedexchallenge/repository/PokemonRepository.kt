package dev.marcosouza.pokedexchallenge.repository

import androidx.lifecycle.LiveData
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import dev.marcosouza.pokedexchallenge.network.CustomRetrofitBuilder
import dev.marcosouza.pokedexchallenge.ui.main.state.MainViewState
import dev.marcosouza.pokedexchallenge.util.ApiSuccessResponse
import dev.marcosouza.pokedexchallenge.util.DataState
import dev.marcosouza.pokedexchallenge.util.GenericApiResponse

object PokemonRepository {

    fun getAllPokemons(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<PokemonResponse, MainViewState>(){
            override fun handleApiSucessResponse(response: ApiSuccessResponse<PokemonResponse>) {
                result.value = DataState.data(data = MainViewState(
                        pokemons = response.body
                    ))
            }
            override fun createCall(): LiveData<GenericApiResponse<PokemonResponse>> {
                return CustomRetrofitBuilder.apiService.getAllPokemons()
            }
        }.asLiveData()
    }

}