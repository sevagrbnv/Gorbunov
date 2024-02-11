package ru.sevagrbnv.cinemaapp.data.local

import androidx.lifecycle.LiveData
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    suspend fun addMovie(movieDbDTO: MovieDbDTO) = movieDao.addMovie(movieDbDTO)

    suspend fun getMovie(movieId: Long): MovieDbDTO = movieDao.getMovie(movieId)

    fun getMovieList(): LiveData<List<MovieDbDTO>> = movieDao.getMovieList()
}