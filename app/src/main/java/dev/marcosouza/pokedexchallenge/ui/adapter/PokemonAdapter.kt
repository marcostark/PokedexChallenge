package dev.marcosouza.pokedexchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_list_item.view.*

class PokemonAdapter(private val interaction: Iteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pokemon_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    fun submitList(list: List<Pokemon>) {
        return differ.submitList(list)
    }

    class PokemonViewHolder constructor(
        itemView: View,
        private val interaction: Iteraction?
    ) : RecyclerView.ViewHolder(itemView) {

     fun bind(item: Pokemon) = with( itemView) {
         itemView.text_name_pokemon.text = item.name.capitalize()
         Glide.with(itemView.context)
             .load(item.getImageUrl())
             .into(itemView.image_pokemon)
     }
    }

    interface Iteraction {
        fun onItemSelected(position: Int, item: Pokemon)
    }
}

