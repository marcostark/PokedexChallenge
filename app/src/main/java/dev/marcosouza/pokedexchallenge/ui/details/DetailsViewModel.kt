package dev.marcosouza.pokedexchallenge.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.marcosouza.pokedexchallenge.model.*
import dev.marcosouza.pokedexchallenge.repository.PokemonRepository
import dev.marcosouza.pokedexchallenge.ui.details.state.DetailsStateEvent
import dev.marcosouza.pokedexchallenge.ui.details.state.DetailsViewState
import dev.marcosouza.pokedexchallenge.util.AbsentLiveData
import dev.marcosouza.pokedexchallenge.util.DataState

class DetailsViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<DetailsStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<DetailsViewState> = MutableLiveData()
    private var _currentPage: Int = 0;

    private var _selectedPokemon = MutableLiveData<Pokemon>()
    private var _selectedAbility = MutableLiveData<Ability>()
    private var _selectedType = MutableLiveData<Type>()
    private var _query = MutableLiveData<String>()

    fun setPokemon(pokemon: Pokemon) {
        _selectedPokemon.value =  pokemon
    }

    fun setAbility(ability: Ability) {
        _selectedAbility.value =  ability
    }

    fun setType(type: Type) {
        _selectedType.value =  type
    }

    val viewState: LiveData<DetailsViewState>
        get() = _viewState

    val dataState: LiveData<DataState<DetailsViewState>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            stateEvent?.let(this::handleStateEvent)
        }

    private fun handleStateEvent(stateEvent: DetailsStateEvent) : LiveData<DataState<DetailsViewState>> {
        return when(stateEvent) {

            is DetailsStateEvent.GetPokemonDetails -> {
                PokemonRepository.getPokemon(_selectedPokemon.value!!.name);
            }

            is DetailsStateEvent.GetPokemonAbilities -> {
                PokemonRepository.getAbilities(_selectedAbility.value?.name.toString())
            }

            is DetailsStateEvent.GetPokemonEvolutions -> {
                PokemonRepository.getEvolutions("1");
            }

            is DetailsStateEvent.GetPokemonType -> {
                println("DEBUG: ${_selectedType.value?.name.toString()}")
                PokemonRepository.getType(_selectedType.value?.name.toString());
            }


            is DetailsStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }



    fun setPokemonDetailData(details: PokemonDetails){
        val update = getCurrentViewStateOrNew()
        update.pokemon = details
        _viewState.value = update
    }

    fun setAbilitiesDetailData(details: PokemonAbility){
        val update = getCurrentViewStateOrNew()
        update.abilities = details
        _viewState.value = update
    }

    fun setPokemonTypeData(type: PokemonType){
        val update = getCurrentViewStateOrNew()
        update.type = type
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): DetailsViewState {
        val value = viewState.value?.let {
            it
        }?: DetailsViewState()
        return value
    }

    fun setStateEvent(event: DetailsStateEvent){
        _stateEvent.value = event
    }

    fun setQuery(query: String){
        _query.value = query
    }
}