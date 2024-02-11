package ru.sevagrbnv.cinemaapp.domain.usecases

import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.MovieRepository
import javax.inject.Inject

class GetMovieFromLocalUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun execute(movieId: Long): List<Movie> =
        repository.getMovieFromLocal(movieId)
}