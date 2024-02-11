package ru.sevagrbnv.cinemaapp.presentation.popularFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.usecases.AddMovieToFavoritesUseCase
import ru.sevagrbnv.cinemaapp.domain.usecases.GetTopFromNetworkUeCase
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getTopFromNetworkUseCase: GetTopFromNetworkUeCase,
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
) : ViewModel() {

    private val _movieList = MutableLiveData<NetworkResult<List<Movie>>>()
    val movieList: LiveData<NetworkResult<List<Movie>>>
        get() = _movieList

    init {
        getTopList()
    }

    private fun getTopList() {
        viewModelScope.launch(Dispatchers.Main) {
            _movieList.value = getTopFromNetworkUseCase.execute()
        }
    }

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            addMovieToFavoritesUseCase.execute(movie)
        }
    }
}
