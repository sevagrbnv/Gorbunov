package ru.sevagrbnv.cinemaapp.domain

import androidx.lifecycle.LiveData
import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult

interface MovieRepository {

    suspend fun getTopFromNetwork(): NetworkResult<List<Movie>>

    fun getFavorites(): LiveData<List<Movie>>

    suspend fun getMovieFromNetwork(movieId: Long): NetworkResult<List<Movie>>

    suspend fun getMovieFromLocal(movieId: Long): List<Movie>

    suspend fun addMovieToFavorites(movie: Movie)
}