package dev.marcosouza.pokedexchallenge.ui.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Abilities
import dev.marcosouza.pokedexchallenge.model.Ability
import kotlinx.android.synthetic.main.ability_list_item.*
import kotlinx.android.synthetic.main.ability_list_item.view.*

class AbilityAdapter(
    pokemons: List<Abilities>,
    private val interaction: Iteraction? = null,
    colorType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pokemonsList = ArrayList<Abilities>()
    private var colorType: Int

    init {
        this.pokemonsList = pokemons as ArrayList<Abilities>
        this.colorType = colorType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ability_list_item,
                parent,
                false
            ),
            interaction,
            colorType
        )
    }

    override fun getItemCount(): Int {
        return this.pokemonsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonViewHolder -> {
                holder.bind(this.pokemonsList.get(position).ability, colorType)
            }
        }
    }

    class PokemonViewHolder constructor(
        itemView: View,
        private val interaction: Iteraction?,
        colorType: Int
    ) : RecyclerView.ViewHolder(itemView) {

     fun bind(item: Ability, colorType: Int) = with( itemView) {

         itemView.setOnClickListener {
             interaction?.onItemSelected(adapterPosition, item)
         }
         itemView.text_pokemon_ability.text = item.name.capitalize()
         text_pokemon_ability.background.setColorFilter(colorType, PorterDuff.Mode.SRC_ATOP);
     }
    }
    interface Iteraction {
        fun onItemSelected(position: Int, item: Ability)
    }
}

