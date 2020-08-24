package dev.marcosouza.pokedexchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonType(

    @Expose
    @SerializedName("name")
    val nameType: String,

    @Expose
    @SerializedName("pokemon")
    val pokemons: List<Pokemons>

) : Parcelable {
    override fun toString(): String {
        return "PokemonType(name='$nameType', pokemons=$pokemons)"
    }
}

@Parcelize
class Pokemons(

    @Expose
    @SerializedName("pokemon")
    val pokemon: Pokemon

) : Parcelable {
    override fun toString(): String {
        return "Pokemon(pokemon=$pokemon)"
    }
}

