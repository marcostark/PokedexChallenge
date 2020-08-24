package dev.marcosouza.pokedexchallenge.ui.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Type
import dev.marcosouza.pokedexchallenge.model.Types
import kotlinx.android.synthetic.main.ability_list_item.view.*
import kotlinx.android.synthetic.main.type_list_item.view.*

class TypeAdapter(
    pokemons: List<Types>,
    private val interaction: Iteraction? = null,
    colorType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var types = ArrayList<Types>()
    private var colorType: Int

    init {
        this.types = pokemons as ArrayList<Types>
        this.colorType = colorType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.type_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun getItemCount(): Int {
        return this.types.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonViewHolder -> {
                holder.bind(this.types.get(position).type, colorType)
            }
        }
    }

    class PokemonViewHolder constructor(
        itemView: View,
        private val interaction: Iteraction?
    ) : RecyclerView.ViewHolder(itemView) {

     fun bind(item: Type, colorType: Int) = with( itemView) {

         itemView.setOnClickListener {
             interaction?.onItemSelected(adapterPosition, item)
         }
         itemView.text_pokemon_type.text = item.name.capitalize()
         text_pokemon_type.background.setColorFilter(colorType, PorterDuff.Mode.SRC_ATOP);
     }
    }
    interface Iteraction {
        fun onItemSelected(position: Int, item: Type)
    }
}

