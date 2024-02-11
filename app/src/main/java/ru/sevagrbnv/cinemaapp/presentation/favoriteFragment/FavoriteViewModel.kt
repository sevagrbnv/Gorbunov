package ru.sevagrbnv.cinemaapp.presentation.favoriteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.usecases.AddMovieToFavoritesUseCase
import ru.sevagrbnv.cinemaapp.domain.usecases.GetFavoritesUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
) : ViewModel() {

    private val _movieList = getFavoritesUseCase.execute()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            addMovieToFavoritesUseCase.execute(movie)
        }
    }

}