package dev.marcosouza.pokedexchallenge.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.ui.details.fragments.details.DetailsFragment
import dev.marcosouza.pokedexchallenge.ui.main.state.DataStateListener
import dev.marcosouza.pokedexchallenge.util.Constants.Companion.INPUT_EXTRA_POKEMON
import dev.marcosouza.pokedexchallenge.util.DataState
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity(),
    DataStateListener {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        val pokemonDetails: Pokemon = requireNotNull(intent.getParcelableExtra(INPUT_EXTRA_POKEMON))
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.setPokemon(pokemonDetails)
        showFragment(DetailsFragment())
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_details_container,
                fragment, "fragment")
            .commit()
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            showProgressBar(it.loading)

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
            details_progress_bar.visibility = View.VISIBLE
        } else {
            details_progress_bar.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}