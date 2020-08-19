package dev.marcosouza.pokedexchallenge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import dev.marcosouza.pokedexchallenge.repository.PokemonRepository
import dev.marcosouza.pokedexchallenge.ui.main.state.MainStateEvent
import dev.marcosouza.pokedexchallenge.ui.main.state.MainViewState
import dev.marcosouza.pokedexchallenge.util.AbsentLiveData
import dev.marcosouza.pokedexchallenge.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            stateEvent?.let(this::handleStateEvent)
        }

    private fun handleStateEvent(stateEvent: MainStateEvent) : LiveData<DataState<MainViewState>> {
        return when(stateEvent) {

            is MainStateEvent.GetPokemonsEvent -> {
                PokemonRepository.getAllPokemons()
            }

            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }

        }
    }

    fun setPokemonsListData(pokemons: PokemonResponse){
        val update = getCurrentViewStateOrNew()
        update.pokemons = pokemons
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        }?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }
}