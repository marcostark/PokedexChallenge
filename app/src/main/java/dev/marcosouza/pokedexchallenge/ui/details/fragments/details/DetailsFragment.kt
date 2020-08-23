package dev.marcosouza.pokedexchallenge.ui.details.fragments.details

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import dev.marcosouza.pokedexchallenge.ui.main.ICallbackListener
import dev.marcosouza.pokedexchallenge.ui.main.MainViewModel
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.ui.main.state.MainStateEvent
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
    -   Ao tocar em um tipo, exibir a lista dos pokemons desse mesmo tipo;
    -   Exibir a cadeia de evolução do Pokémon;
    -   Exibir spinner (combobox) que permita selecionar as variações do Pokémon (ao selecionar uma variação, o app deve carregar automaticamente os dados da variação selecionada);
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        activity?.actionBar?.setBackgroundDrawable(ColorDrawable(Color.rgb(248, 248, 248)))
        val id = pokemon.id
        val stats = pokemon.stats
        val types = pokemon.types
        val abilities = pokemon.abilities

        // Tipos do pokemon
        val color: Int = PokemonTypesUtils.getColorByType(types[0].type.name)
        val colorType = PokemonTypesUtils.getColor(color, resources)

        header.background.setColorFilter(colorType, PorterDuff.Mode.SRC_ATOP);

        text_detail_name_pokemon.text = pokemon.name.capitalize()
        text_detail_id_pokemon.text = "#" + id

        // Tipos
        loadTypes(types, colorType)

        // Habilidades
        loadAbilities(abilities, colorType)

        // Informações básicas do pokemon
        text_national_id.text = pokemon.id
        text_height.text = pokemon.height.toString()
        text_weight.text = pokemon.weight.toString()

        // Estatisticas basicas do pokemon
        text_hp.text = stats[0].baseStat
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
            println("DEBUG: Datasource: {$dataState}")

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
                this.callbackListener.onCallDialogAbilityDetails(abilities)
            }
        })
    }

    private fun loadAbilities(abilities: List<Abilities>, colorType: Int) {
        recycler_view_abilities.apply {
            layoutManager = GridLayoutManager(activity, 2)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            pokemonAdapter = AbilityAdapter(abilities, this@DetailsFragment, colorType)
            adapter = pokemonAdapter
        }
    }

    private fun loadTypes(types: List<Types>, colorType: Int) {
        recycler_view_types.apply {
            layoutManager = GridLayoutManager(activity, 2)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            typeAdapter = TypeAdapter(types, this@DetailsFragment, colorType)
            adapter = typeAdapter
        }
    }

    private fun loadEvolution(pokemons: List<Pokemon>) {
        recycler_view_evolutions.apply {
            layoutManager = GridLayoutManager(activity, 2)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            evolutionAdapter = EvolutionAdapter(pokemons)
            adapter = evolutionAdapter
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
        Toast.makeText(activity, "Item: $item", Toast.LENGTH_LONG).show()
    }

    fun setOnPokemonAbilityClickListener(OnPokemonAbilityClickListener: ICallbackDetailsListener) {
        this.callbackListener = OnPokemonAbilityClickListener
    }
}