package dev.marcosouza.pokedexchallenge.ui.main.fragments.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonDetails
import dev.marcosouza.pokedexchallenge.ui.adapter.PokemonAdapter
import dev.marcosouza.pokedexchallenge.ui.main.ICallbackListener
import dev.marcosouza.pokedexchallenge.ui.main.MainViewModel
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.ui.main.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateListener: DataStateListener

    private lateinit var callbackListener: ICallbackListener

    /*
    * TODO
    *   Tela com os detalhes do Pokémon ○ Nome e ID;
    -   Carrossel com as fotos disponíveis do Pokémon
    -   View com os stats do Pokémon (hp, attack, defense, special attack, special defense, speed);
    -   Exibir suas Habilidades (Run Away, Adaptability, Synchronize etc);
    -   Ao tocar em uma habilidade, exibir um modal com a descrição;
    -   Exibir seus tipos (electric, ground, water, fire etc);
    -   Ao tocar em um tipo, exibir a lista dos pokemons desse mesmo tipo;
    -   Exibir a cadeia de evolução do Pokémon;
    -   Exibir spinner (combobox) que permita selecionar as variações do Pokémon (ao selecionar uma variação, o app deve carregar automaticamente os dados da variação selecionada);
    * */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid Activity!")

        subscribeObververs()
        triggerGetPokemonDetailsEvent()
    }

    private fun updateUi(pokemon: PokemonDetails) {
        println("DEBUG $pokemon")

        context?.let {
            Glide.with(it)
                .load(pokemon.getImageUrl())
                .into(image_detail_pokemon)
        }
        text_detail_name_pokemon.text = pokemon.name
    }

    private fun triggerGetPokemonDetailsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetPokemonDetails())
    }

    private fun subscribeObververs() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: Datasource: {$dataState}")

            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.pokemon?.let { pokemons ->
                        viewModel.setPokemonDetailData(pokemons)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.pokemon?.let { pokemon ->
                updateUi(pokemon)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }
}