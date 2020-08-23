package dev.marcosouza.pokedexchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.Pokemons
import kotlinx.android.synthetic.main.type_pokemon_list_item.view.*


class PokemonTypeAdapter(
    pokemons: List<Pokemons>?,
    private val interaction: Iteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pokemonsList = ArrayList<Pokemons>()

    init {
        this.pokemonsList = pokemons as ArrayList<Pokemons>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.type_pokemon_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    fun updateListPokemons(pokemons: List<Pokemons>){
        val initPosition = pokemonsList.size
        pokemonsList.addAll(pokemons)
        notifyItemRangeInserted(initPosition, pokemonsList.size)
    }

    override fun getItemCount(): Int {
        return this.pokemonsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonViewHolder -> {
                holder.bind(this.pokemonsList.get(position).pokemon)
            }
        }
    }

    class PokemonViewHolder constructor(
        itemView: View,
        private val interaction: Iteraction?
    ) : RecyclerView.ViewHolder(itemView) {

     fun bind(item: Pokemon) = with( itemView) {

         itemView.setOnClickListener {
             interaction?.onItemSelected(adapterPosition, item)
         }

         itemView.text_type_pokemon_name.text = item.name.capitalize()
         itemView.text_type_pokemon_id.text = "#${item.getId()}"
         Glide.with(itemView.context)
             .load(item.getImageUrl())
             .into(itemView.image_type_pokemon)
     }
    }
    interface Iteraction {
        fun onItemSelected(position: Int, item: Pokemon)
    }
}

