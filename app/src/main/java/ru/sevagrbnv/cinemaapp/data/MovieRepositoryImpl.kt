package ru.sevagrbnv.cinemaapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.sevagrbnv.cinemaapp.data.local.LocalDataSource
import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult
import ru.sevagrbnv.cinemaapp.data.remote.RemoteDataSource
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val mapper: Mapper
) : MovieRepository {

    override suspend fun getTopFromNetwork(): NetworkResult<List<Movie>> {
        return try {
            val response = remoteDataSource.getTop100()
            if (response.isSuccessful) {
                val body = response.body() ?: throw RuntimeException("Empty body of response")
                val a = mapper.top100ResponseToMovie(body)
                NetworkResult.Success(a)
            } else {
                NetworkResult.Error(
                    data = null,
                    message = "Api call failed: ${response.code()} ${response.message()}"
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(
                data = null,
                message = e.message.toString()
            )
        }
    }

    override suspend fun getMovieFromNetwork(movieId: Long): NetworkResult<List<Movie>> {
        return try {
            val response = remoteDataSource.getMovie(movieId)
            if (response.isSuccessful) {
                val body = response.body() ?: throw RuntimeException("Network Error")
                NetworkResult.Success(listOf(mapper.movieResponseToMovie(body)))
            } else NetworkResult.Error(
                data = null,
                message = "Api call failed: ${response.code()} ${response.message()}"
            )
        } catch (e: Exception) {
            NetworkResult.Error(
                data = null,
                message = e.message.toString()
            )
        }
    }

    override fun getFavorites(): LiveData<List<Movie>> =
        localDataSource.getMovieList().map {
            mapper.listMovieDbTDOtoListMovie(it)
        }

    override suspend fun getMovieFromLocal(movieId: Long): List<Movie> =
        listOf(mapper.movieDbTDOtoMovie(localDataSource.getMovie(movieId)))


    override suspend fun addMovieToFavorites(movie: Movie) =
        localDataSource.addMovie(mapper.movieToMovieDbTDO(movie))

}