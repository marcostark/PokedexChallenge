package dev.marcosouza.pokedexchallenge.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import dev.marcosouza.pokedexchallenge.R
import dev.marcosouza.pokedexchallenge.model.Pokemon
import dev.marcosouza.pokedexchallenge.model.PokemonResponse
import kotlinx.android.synthetic.main.pokemon_list_item.view.*


class PokemonAdapter(
    pokemons: List<Pokemon>?,
    private val interaction: Iteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pokemonsList = ArrayList<Pokemon>()

    init {
        this.pokemonsList = pokemons as ArrayList<Pokemon>
    }

//    private val CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
//
//        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
//            return oldItem.name == newItem.name
//        }
//
//        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    private val differ =
//        AsyncListDiffer(
//            PokemonRecyclerChangeCallback(this),
//            AsyncDifferConfig.Builder(CALLBACK).build()
//        )


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
        //return differ.currentList.size
        return this.pokemonsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PokemonViewHolder -> {
                //holder.bind(differ.currentList.get(position))
                holder.bind(this.pokemonsList.get(position))
            }
        }
    }

//    fun submitList(list: List<Pokemon>) {
//        differ.submitList(list)
//    }

    fun updateListPokemons(pokemons: List<Pokemon>){
        val initPosition = pokemonsList.size
        pokemonsList.addAll(pokemons)
        notifyItemRangeInserted(initPosition, pokemonsList.size)
    }
//
//
//    internal inner class PokemonRecyclerChangeCallback(
//        private val adapter: PokemonAdapter
//    ) : ListUpdateCallback {
//
//        override fun onChanged(position: Int, count: Int, payload: Any?) {
//            adapter.notifyItemRangeChanged(position, count, payload)
//        }
//
//        override fun onInserted(position: Int, count: Int) {
//            adapter.notifyItemRangeChanged(position, count)
//        }
//
//        override fun onMoved(fromPosition: Int, toPosition: Int) {
//            adapter.notifyDataSetChanged()
//        }
//
//        override fun onRemoved(position: Int, count: Int) {
//            adapter.notifyDataSetChanged()
//        }
//    }

    class PokemonViewHolder constructor(
        itemView: View,
        private val interaction: Iteraction?
    ) : RecyclerView.ViewHolder(itemView) {

     fun bind(item: Pokemon) = with( itemView) {

         itemView.setOnClickListener {
             interaction?.onItemSelected(adapterPosition, item)
         }

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

