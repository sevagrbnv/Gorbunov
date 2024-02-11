package ru.sevagrbnv.cinemaapp.presentation.movieFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sevagrbnv.cinemaapp.data.remote.NetworkResult
import ru.sevagrbnv.cinemaapp.domain.Movie
import ru.sevagrbnv.cinemaapp.domain.usecases.GetMovieFromLocalUseCase
import ru.sevagrbnv.cinemaapp.domain.usecases.GetMovieFromNetworkUseCase
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieFromLocalUseCase: GetMovieFromLocalUseCase,
    private val getMovieFromNetworkUseCase: GetMovieFromNetworkUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<List<Movie>>()

    private val _error = MutableLiveData<Boolean>(false)

    fun getMovie(id: Long): LiveData<List<Movie>> {
        var result: NetworkResult<List<Movie>>? = null
        val resultFlag = viewModelScope.launch {
            result = getMovieFromNetworkUseCase.execute(id)
        }

        viewModelScope.launch {
            resultFlag.join()
            if (result is NetworkResult.Success) {
                _movie.value = result?.data ?: throw RuntimeException()
            } else if (result is NetworkResult.Error) {
                try {
                    getFromLocal(id)
                } catch (e: Exception) {
                    _error.value = true
                }
            }
        }
        return _movie
    }

    fun getFromLocal(id: Long = 1115471) {
        viewModelScope.launch(Dispatchers.Main) {
            _movie.value = getMovieFromLocalUseCase.execute(id)
        }
    }
}