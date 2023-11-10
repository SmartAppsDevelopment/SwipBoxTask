package com.example.templatesampleapp.utils


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Either<out R> {

    data class Success<out T>(val data: T) : Either<T>()
    data class Error(val exception: String) : Either<Nothing>()
    //data class ErrorMsg(val exception: String) : Either<Nothing>()

    inline fun onSuccess(block: (R) -> Unit): Either<R> = apply {
        if (this is Success) {
            block(data)
        }
    }

    inline fun onFailure(block: (String) -> Unit): Either<R> = apply {
        if (this is Error) {
            block(this.exception)
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
