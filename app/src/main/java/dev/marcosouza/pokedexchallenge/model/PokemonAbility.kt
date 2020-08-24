package dev.marcosouza.pokedexchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonAbility(

    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("effect_entries")
    val entries: List<Effect>

) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokemonAbility

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "PokemonAbility(id='$id', name='$name', entries=$entries)"
    }


}

@Parcelize
class Effect (

    @Expose
    @SerializedName("effect")
    val effet: String

): Parcelable {
    override fun toString(): String {
        return "Effect(effet='$effet')"
    }
}
