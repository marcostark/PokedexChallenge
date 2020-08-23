package dev.marcosouza.pokedexchallenge.ui.details.fragments.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.ui.adapter.PokemonTypeAdapter
import dev.marcosouza.pokedexchallenge.ui.details.DetailsViewModel
import dev.marcosouza.pokedexchallenge.ui.details.state.DetailsStateEvent
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_type_list_dialog_list_dialog.*

class TypeListDialogFragment : Fragment(),
    PokemonTypeAdapter.Iteraction
{
    private lateinit var viewModel: DetailsViewModel
    private lateinit var dataStateListener: DataStateListener
    private lateinit var pokemonTypeAdapter: PokemonTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_type_list_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(DetailsViewModel::class.java)
        }?:throw Exception("Invalid Activity!")

        subscribeObververs()
        triggerGetPokemonsByType()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view_pokemon_types.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            pokemonTypeAdapter = PokemonTypeAdapter(ArrayList(), this@TypeListDialogFragment)
            adapter = pokemonTypeAdapter

        }
    }

    private fun subscribeObververs() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataStateListener.onDataStateChange(dataState)
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.type?.let { type ->
                        viewModel.setPokemonTypeData(type)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.type?.let { type ->
                println("DEBUG: Type: {${type.pokemons}}")
                pokemonTypeAdapter.updateListPokemons(type.pokemons)
            }
        })
    }

    private fun triggerGetPokemonsByType() {
        viewModel.setStateEvent(DetailsStateEvent.GetPokemonType())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onItemSelected(position: Int, item: Pokemon) {
        Toast.makeText(context, item.name, Toast.LENGTH_LONG).show()
    }

}