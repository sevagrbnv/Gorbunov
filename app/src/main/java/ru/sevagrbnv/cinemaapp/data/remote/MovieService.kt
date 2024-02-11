package ru.sevagrbnv.cinemaapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.movie.MovieResponse
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.top100.Top100Response

interface MovieService {

    @GET("/api/v2.2/films/top")
    suspend fun getTop100(
        @Query("type") type: String = TYPE,
        @Header("x-api-key") authToken: String = TOKEN
    ): Response<Top100Response>

    @GET("/api/v2.2/films/{movieId}")
    suspend fun getMovie(
        @Path("movieId") movieId: Long,
        @Header("x-api-key") authToken: String = TOKEN
    ): Response<MovieResponse>

    companion object {
        private const val TYPE = "TOP_100_POPULAR_FILMS"
        private const val TOKEN = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }
}