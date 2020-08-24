package dev.marcosouza.pokedexchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import kotlinx.android.synthetic.main.evolutions_list_item.view.*
import kotlinx.android.synthetic.main.pokemon_list_item.view.*


class EvolutionAdapter(
    pokemons: List<Pokemon>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pokemonsList = ArrayList<Pokemon>()

    init {
        this.pokemonsList = pokemons as ArrayList<Pokemon>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        //return differ.currentList.size
        return this.pokemonsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonViewHolder -> {
                holder.bind(this.pokemonsList.get(position))
            }
        }
    }

    fun updateListPokemons(pokemons: List<Pokemon>){
        val initPosition = pokemonsList.size
        pokemonsList.addAll(pokemons)
        notifyItemRangeInserted(initPosition, pokemonsList.size)
    }

    fun clearListPokemons() {
        pokemonsList.clear()
        notifyDataSetChanged()
    }

    class PokemonViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

     fun bind(item: Pokemon) = with( itemView) {

         itemView.text_name_pokemon.text = item.name.capitalize()
         itemView.text_name_pokemon_evolution_id.text = "1"
         Glide.with(itemView.context)
             .load(item.getImageUrl())
             .into(itemView.image_pokemon)
     }
    }
}

