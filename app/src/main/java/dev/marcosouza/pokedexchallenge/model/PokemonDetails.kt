package dev.marcosouza.pokedexchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonDetails(

    @Expose
    @SerializedName("id")
    val id: Int,

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
    val abilities: List<Abilities>,

    @Expose
    @SerializedName("types")
    val types: List<Types>,

    @Expose
    @SerializedName("stats")
    val stats: List<Stats>


) : Parcelable {

    fun getImageUrl(): String {
        val id = id
        //return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        return "https://pokeres.bastionbot.org/images/pokemon/$id.png"
    }

    fun getIdFormatted():String = String.format("#%03d", id)
    fun getWeightFormatted():String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightFormatted():String = String.format("%.1f M", height.toFloat() / 10)

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

    override fun toString(): String {
        return "PokemonDetails(id='$id', name='$name', weight=$weight, height=$height, order=$order, abilities=$abilities, types=$types, stats=$stats)"
    }
}

@Parcelize
class Abilities(

    @Expose
    @SerializedName("ability")
    val ability: Ability

) : Parcelable { }

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

    override fun toString(): String {
        return "Ability(name='$name', url='$url')"
    }
}


@Parcelize
class Types(

    @Expose
    @SerializedName("slot")
    val slot: String,

    @Expose
    @SerializedName("type")
    val type: Type

) : Parcelable { }

@Parcelize
class Type(

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

) : Parcelable {
    override fun toString(): String {
        return "Stats(baseStat='$baseStat', effort='$effort', stat=$stat)"
    }
}

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