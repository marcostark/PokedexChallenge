package dev.marcosouza.pokedexchallenge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonDetails
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import dev.marcosouza.pokedexchallenge.repository.PokemonRepository
import dev.marcosouza.pokedexchallenge.ui.main.state.MainStateEvent
import dev.marcosouza.pokedexchallenge.ui.main.state.MainViewState
import dev.marcosouza.pokedexchallenge.util.AbsentLiveData
import dev.marcosouza.pokedexchallenge.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    private var _currentPage: Int = 0;

    private var _selectedPokemon = MutableLiveData<Pokemon>()
    private var _query = MutableLiveData<String>()

    fun setPokemon(pokemon: Pokemon) {
        _selectedPokemon.value =  pokemon
    }

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            stateEvent?.let(this::handleStateEvent)
        }

    private fun handleStateEvent(stateEvent: MainStateEvent) : LiveData<DataState<MainViewState>> {
        return when(stateEvent) {

            is MainStateEvent.GetPokemonsEvent -> {
                PokemonRepository.getAllPokemons(_currentPage)
            }

            is MainStateEvent.GetPokemonDetails -> {
                PokemonRepository.getPokemon(_selectedPokemon.value!!.name);
            }

            is MainStateEvent.GetSearchPokemon -> {
                PokemonRepository.getSearchPokemon(_query.value.toString());
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

    fun setPokemonDetailData(details: PokemonDetails){
        val update = getCurrentViewStateOrNew()
        update.pokemon = details
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

    fun nextPage() {
        incrementPageNumber()
        println("DEBUG: BlogFragment: attempting to load next page...")
        setStateEvent(MainStateEvent.GetPokemonsEvent())
    }

    private fun incrementPageNumber(){
        _currentPage++
    }

    fun setQuery(query: String){
        _query.value = query
    }


//    fun loadPokemons() {
//        PokemonRepository.getAllPokemons(page)
//    }
}