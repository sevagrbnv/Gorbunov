package ru.sevagrbnv.cinemaapp.domain.usecases

import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.MovieRepository
import javax.inject.Inject

class AddMovieToFavoritesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun execute(movie: Movie) =
        repository.addMovieToFavorites(movie)
}