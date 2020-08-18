package dev.marcosouza.pokedexchallenge.util


/**
 * Usado como invólucro para dados expostos por meio de um LiveData  que representa um evento
 */
class Event<T>(private val content: T) {

    private var hasBeenHandled = false
        private set // Permite leitura externa mas não gravação

    /**
     * Retorna um conteúdo e impede o seu uso novamente
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Retonar
     */
    fun peekContent(): T = content

    override fun toString(): String {
        return "Event(content=$content,hasBeenHandled=$hasBeenHandled)"
    }

    companion object{

        // Evento nao necessario caso não aja dados
        fun <T> dataEvent(data: T?): Event<T>?{
            data?.let {
                return Event(it)
            }
            return null
        }

        // Evento nao necessario caso não aja mensagem
        fun messageEvent(message: String?): Event<String>?{
            message?.let{
                return Event(message)
            }
            return null
        }
    }
}