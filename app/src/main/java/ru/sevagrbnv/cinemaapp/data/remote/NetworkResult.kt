package ru.sevagrbnv.cinemaapp.data.remote

import ru.sevagrbnv.cinemaapp.domain.Movie

sealed class NetworkResult<T>(
    val data: List<Movie>? = null,
    val message: String? = null
) {
    class Success(data: List<Movie>): NetworkResult<List<Movie>>(data)
    class Error<T>(data: T?, message: String?): NetworkResult<T>(null, message)
    class Loading<T> : NetworkResult<T>()
}