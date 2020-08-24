package dev.marcosouza.pokedexchallenge.util

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import dev.marcosouza.pokedexchallenge.R

object PokemonTypesUtils {

    fun getColorByType(type: String): Int {
        return when(type) {
            "normal" -> R.color.normal
            "fighting" -> R.color.fighting
            "flying" -> R.color.flying
            "poison" -> R.color.poison
            "ground" -> R.color.ground
            "rock" -> R.color.rock
            "bug" -> R.color.bug
            "ghost" -> R.color.ghost
            "steel" -> R.color.steel
            "fire" -> R.color.fire
            "water" -> R.color.water
            "grass" -> R.color.grass
            "electric" -> R.color.electric
            "psychic" -> R.color.psychic
            "ice" -> R.color.ice
            "dragon" -> R.color.dragon
            "dark" -> R.color.dark
            "fairy" -> R.color.fairy
            "shadow" -> R.color.shadow
            else -> R.color.unknown
        }
    }

    fun getColor(color: Int, resources: Resources): Int {
        return ResourcesCompat.getColor(resources, color, null)
    }
}

