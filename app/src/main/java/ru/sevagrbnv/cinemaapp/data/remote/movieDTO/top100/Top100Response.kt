package ru.sevagrbnv.cinemaapp.data.remote.movieDTO.top100

data class Top100Response(
    val films: List<Film>,
    val pagesCount: Int
)