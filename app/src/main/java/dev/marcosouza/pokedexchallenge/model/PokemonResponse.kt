package dev.marcosouza.pokedexchallenge.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonResponse(

    @Expose
    @SerializedName("count")
    val count: Int,

    @Expose
    @SerializedName("next")
    val next: String?,

    @Expose
    @SerializedName("previous")
    val previous: String?,

    @Expose
    @SerializedName("results")
    val results: List<Pokemon>
) : Parcelable {
    override fun toString(): String {
        return "PokemonResponse(count=$count, next=$next, previous=$previous, results=$results)"
    }
}