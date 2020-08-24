package dev.marcosouza.pokedexchallenge.ui.details.fragments.details

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.*
import dev.marcosouza.pokedexchallenge.ui.adapter.AbilityAdapter
import dev.marcosouza.pokedexchallenge.ui.adapter.EvolutionAdapter
import dev.marcosouza.pokedexchallenge.ui.adapter.TypeAdapter
import dev.marcosouza.pokedexchallenge.ui.details.DetailsViewModel
import dev.marcosouza.pokedexchallenge.ui.details.ICallbackDetailsListener
import dev.marcosouza.pokedexchallenge.ui.details.state.DetailsStateEvent
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.util.PokemonTypesUtils
import dev.marcosouza.pokedexchallenge.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(),
    AbilityAdapter.Iteraction,
    TypeAdapter.Iteraction{

    private lateinit var viewModel: DetailsViewModel
    private lateinit var dataStateListener: DataStateListener

    private lateinit var pokemonAdapter: AbilityAdapter
    private lateinit var typeAdapter: TypeAdapter
    private lateinit var evolutionAdapter: EvolutionAdapter
    private lateinit var callbackListener: ICallbackDetailsListener

    /*
    * TODO
    -   Carrossel com as fotos disponíveis do Pokémon
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
            ViewModelProvider(this).get(DetailsViewModel::class.java)
        }?:throw Exception("Invalid Activity!")

        subscribeObververs()
        triggerGetPokemonDetailsEvent()
    }

    private fun updateUi(pokemon: PokemonDetails) {

        context?.let {
            Glide.with(it)
                .load(pokemon.getImageUrl())
                .into(image_detail_pokemon)
        }

        val id = pokemon.id
        val stats = pokemon.stats
        val types = pokemon.types
        val abilities = pokemon.abilities

        // Tipos do pokemon
        val color: Int = PokemonTypesUtils.getColorByType(types[0].type.name)
        val colorType = PokemonTypesUtils.getColor(color, resources)

        header.background.setColorFilter(colorType, PorterDuff.Mode.SRC_ATOP);

        text_detail_name_pokemon.text = pokemon.name.capitalize()
        text_detail_id_pokemon.text = pokemon.getIdFormatted()

        // Tipos
        loadTypes(types, colorType)

        // Habilidades
        loadAbilities(abilities, colorType)

        // Informações básicas do pokemon
        text_national_id.text = pokemon.getIdFormatted()
        text_height.text = pokemon.getHeightFormatted()
        text_weight.text = pokemon.getWeightFormatted()

        // Estatisticas basicas do pokemon
        text_hp.text = stats[0].baseStat
        //text_hp.progress = stats[0].baseStat.toInt()
        text_atk.text = stats[1].baseStat
        text_def.text = stats[2].baseStat
        text_satk.text = stats[3].baseStat
        text_sdef.text = stats[4].baseStat
        text_spd.text = stats[5].baseStat
    }

    private fun triggerGetPokemonDetailsEvent() {
        viewModel.setStateEvent(DetailsStateEvent.GetPokemonDetails())
    }

    private fun subscribeObververs() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.pokemon?.let { pokemons ->
                        viewModel.setPokemonDetailData(pokemons)
                    }

                    mainViewState.abilities?.let { abilities ->
                        viewModel.setAbilitiesDetailData(abilities)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.pokemon?.let { pokemon ->
                updateUi(pokemon)
            }
            viewState.abilities?.let { abilities ->
                // TODO Bug : ao voltar da tela de listagem de pokemons por tipo
                println("DEBUG: Abilities: $abilities")
                this.callbackListener.onCallDialogAbilityDetails(abilities)
            }
        })
    }

    private fun loadAbilities(abilities: List<Abilities>, colorType: Int) {
        recycler_view_abilities.apply {
            val span = if(abilities.size % 2 == 0) 2 else 1
            layoutManager = GridLayoutManager(activity, span)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            pokemonAdapter = AbilityAdapter(abilities, this@DetailsFragment, colorType)
            adapter = pokemonAdapter
        }
    }

    private fun loadTypes(types: List<Types>, colorType: Int) {
        recycler_view_types.apply {
            val span = if(types.size % 2 == 0) 2 else 1
            layoutManager = GridLayoutManager(activity, span)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            typeAdapter = TypeAdapter(types, this@DetailsFragment, colorType)
            adapter = typeAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onItemSelected(position: Int, item: Ability) {
        this.callbackListener.onCallAbilityDetails(item)
    }

    override fun onItemSelected(position: Int, item: Type) {
       this.callbackListener.onCallPokemonsByType(item)
    }

    fun setOnPokemonAbilityClickListener(OnPokemonAbilityClickListener: ICallbackDetailsListener) {
        this.callbackListener = OnPokemonAbilityClickListener
    }
}