package ru.sevagrbnv.cinemaapp.data.remote

import retrofit2.Response
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.movie.MovieResponse
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.top100.Top100Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieService: MovieService
) {
    suspend fun getTop100(): Response<Top100Response> {
        return movieService.getTop100()
    }

    suspend fun getMovie(movieId: Long): Response<MovieResponse> {
        return movieService.getMovie(movieId = movieId)
    }
}