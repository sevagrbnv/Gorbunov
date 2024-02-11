package ru.sevagrbnv.cinemaapp.data

import ru.sevagrbnv.cinemaapp.data.local.MovieDbDTO
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.movie.MovieResponse
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.top100.Film
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.top100.Top100Response
import ru.sevagrbnv.cinemaapp.domain.Movie

class Mapper {

    fun top100ResponseToMovie(
        top100Response: Top100Response,
    ): List<Movie> {
        return top100Response.films.map {
            filmToMovie(it)
        }
    }

    fun movieResponseToMovie(movieResponse: MovieResponse) = Movie(
        id = movieResponse.kinopoiskId.toLong(),
        name = movieResponse.nameRu,
        posterUrl = movieResponse.posterUrl,
        description = movieResponse.description,
        country = movieResponse.countries.joinToString(", "),
        genre = movieResponse.genres.joinToString(", "),
        year = movieResponse.year.toString()
    )

    private fun filmToMovie(film: Film): Movie {
        return Movie(
            id = film.filmId,
            name = film.nameRu,
            posterUrl = film.posterUrlPreview,
            country =  film.countries.joinToString { ", " },
            genre = film.genres.joinToString { ", " } ,
            year = film.year,
            isLiked = false
        )
    }

    fun movieToMovieDbTDO(movie: Movie) = MovieDbDTO(
        id = movie.id,
        country = movie.country,
        genre = movie.genre,
        name = movie.name,
        posterUrl = movie.posterUrl,
        year = movie.year,
        description = movie.description
    )

    fun movieDbTDOtoMovie(movieDbDTO: MovieDbDTO) = Movie(
        id = movieDbDTO.id,
        country = movieDbDTO.country,
        genre = movieDbDTO.genre,
        name = movieDbDTO.name,
        posterUrl = movieDbDTO.posterUrl,
        year = movieDbDTO.year,
        description = movieDbDTO.description,
        isLiked = true
    )

    fun listMovieDbTDOtoListMovie(list: List<MovieDbDTO>) = list.map {
        movieDbTDOtoMovie(it)
    }
}