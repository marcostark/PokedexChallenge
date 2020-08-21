package dev.marcosouza.pokedexchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonDetails(

    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("weight")
    val weight: Int,

    @Expose
    @SerializedName("height")
    val height: Int,

    @Expose
    @SerializedName("order")
    val order: Int,

    @Expose
    @SerializedName("abilities")
    val abilities: List<Ability>,

    @Expose
    @SerializedName("types")
    val types: List<Types>,

    @Expose
    @SerializedName("stats")
    val stats: List<Stats>


) : Parcelable {

    fun getImageUrl(): String {
        val id = id
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }

    override fun toString(): String {
        return "Pokemon(name='$name', id='$id')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokemonDetails

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

@Parcelize
class Ability(

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("url")
    val url: String

) : Parcelable {

    fun getDetailAbility(): String {
        val id = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}

@Parcelize
class Types(

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("url")
    val url: String

) : Parcelable {

    fun getDetailTypes(): String {
        val id = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}

@Parcelize
class Stats(

    @Expose
    @SerializedName("base_stat")
    val baseStat: String,

    @Expose
    @SerializedName("effort")
    val effort: String,

    @Expose
    @SerializedName("stat")
    val stat: Stat

) : Parcelable { }

@Parcelize
class Stat(

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("url")
    val url: String

) : Parcelable {

    fun getDetailStat(): String {
        val id = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}