package ru.sevagrbnv.cinemaapp.domain.usecases

import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.MovieRepository
import javax.inject.Inject

class GetTopFromNetworkUeCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun execute(): NetworkResult<List<Movie>> = repository.getTopFromNetwork()
}