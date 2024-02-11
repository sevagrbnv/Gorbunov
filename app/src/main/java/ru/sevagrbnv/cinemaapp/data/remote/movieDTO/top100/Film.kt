package ru.sevagrbnv.cinemaapp.data.remote.movieDTO.top100

import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.Country
import ru.sevagrbnv.cinemaapp.data.remote.movieDTO.Genre

data class Film(
    val countries: List<Country>,
    val filmId: Long,
    val filmLength: String,
    val genres: List<Genre>,
    val isAfisha: Int,
    val isRatingUp: Any,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingChange: Any,
    val ratingVoteCount: Int,
    val year: String
)