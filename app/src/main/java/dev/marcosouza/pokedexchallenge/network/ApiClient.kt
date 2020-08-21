package dev.marcosouza.pokedexchallenge.network

class ApiClient constructor(
    private val service: ApiService
){

    fun getAllPokemons(
        page: Int
    ) = service.getAllPokemons(
        limit = PAGE_SIZE,
        offset = page * PAGE_SIZE
    )

    companion object {
        private const val PAGE_SIZE = 20
    }
}