package dev.marcosouza.pokedexchallenge.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import dev.marcosouza.pokedexchallenge.util.*
import dev.marcosouza.pokedexchallenge.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject, ViewStateType>{

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(IO) {
            delay(TESTING_NETWORK_DELAY)
            withContext(Main){
                val apiResponse = createCall()
                result.addSource(apiResponse){response ->
                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>){
        when(response){
            is ApiSuccessResponse -> {
                handleApiSucessResponse(response)
            }

            is ApiErrorResponse -> {
                println("DEBUG: NetworkingBoundResource: ${response.errorMessage}")
                onReturnError("HTTP 204. Return Nothing")
            }

            is ApiEmptyResponse -> {
                println("DEBUG: NetworkingBoundResource: HTTP 204. Return Nothing")
                onReturnError("HTTP 204. Return Nothing")
            }
        }
    }

    private fun onReturnError(message: String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSucessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

}