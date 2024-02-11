package ru.sevagrbnv.cinemaapp.domain.usecases

import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.MovieRepository
import javax.inject.Inject

class GetMovieFromNetworkUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun execute(movieId: Long): NetworkResult<List<Movie>> =
        repository.getMovieFromNetwork(movieId)
}