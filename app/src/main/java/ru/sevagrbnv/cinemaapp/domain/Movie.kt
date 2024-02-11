package ru.sevagrbnv.cinemaapp.domain

data class Movie(
    val id: Long = 0,
    val name: String,
    val posterUrl: String,
    val year: String,
    val genre: String,
    val country: String,

    var description: String = "Для просмотра описания неоходимо подключение к интернету",
    var isLiked: Boolean = false
)
