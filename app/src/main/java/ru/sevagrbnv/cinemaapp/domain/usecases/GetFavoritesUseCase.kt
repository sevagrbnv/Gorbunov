package ru.sevagrbnv.cinemaapp.domain.usecases

import androidx.lifecycle.LiveData
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.MovieRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun execute(): LiveData<List<Movie>> =
        repository.getFavorites()
}