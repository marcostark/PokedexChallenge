package dev.marcosouza.pokedexchallenge.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.ui.adapter.PokemonAdapter
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.ui.main.state.MainStateEvent
import dev.marcosouza.pokedexchallenge.util.InfiniteScrollListener
import dev.marcosouza.pokedexchallenge.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(),
    PokemonAdapter.Iteraction{

    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener
    lateinit var pokemonAdapter: PokemonAdapter

    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid Activity!")

        subscribeObververs()
        triggerGetAllPokemonsEvent()
        initRecyclerView()
    }

    private fun initRecyclerView() {
       recycler_view.apply {
           layoutManager = GridLayoutManager(activity, 2)
           val topSpacingItemDecoration = TopSpacingItemDecoration(30)
           addItemDecoration(topSpacingItemDecoration)
           pokemonAdapter = PokemonAdapter(ArrayList(), this@MainFragment)
           addOnScrollListener(InfiniteScrollListener({loadData()}, this.layoutManager as GridLayoutManager))
           adapter = pokemonAdapter

       }
    }

    fun loadData() {
        viewModel.nextPage()
    }

    private fun subscribeObververs() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: Datasource: {$dataState}")

            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.pokemons?.let { pokemons ->
                        viewModel.setPokemonsListData(pokemons)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.pokemons?.let { pokemons ->
                pokemonAdapter.updateListPokemons(pokemons.results)
//               pokemonAdapter.apply {
//                   submitList(
//                       pokemons.results
//                   )
//               }
            }
        })
    }

    private fun triggerGetAllPokemonsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetPokemonsEvent())
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
        println("DEBUG: $item")
    }
}