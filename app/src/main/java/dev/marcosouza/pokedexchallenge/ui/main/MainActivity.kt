package dev.marcosouza.pokedexchallenge.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.ui.main.fragments.details.DetailsFragment
import dev.marcosouza.pokedexchallenge.ui.main.fragments.main.MainFragment
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    DataStateListener,
    ICallbackListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showFragment(MainFragment())
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment, "fragment")
            .commit()
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            // handle loading
            showProgressBar(it.loading)

            //handle message
            it.message?.let{message ->
                message.getContentIfNotHandled()?.let(this::showToast)
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(isVisible: Boolean){
        if(isVisible){
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    override fun onAttachFragment(fragment: Fragment) {
        when(fragment) {
            is MainFragment -> fragment.setOnPokemonDetailClickListener(this)
        }
    }

    override fun onCallBackLaunchDetails(pokemon: Pokemon) {
        println("DEBUG: ITEM: $pokemon")
        viewModel.setPokemon(pokemon)
        this.showFragment(DetailsFragment())
    }
}