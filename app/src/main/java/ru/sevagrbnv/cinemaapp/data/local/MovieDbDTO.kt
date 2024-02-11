package ru.sevagrbnv.cinemaapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieDbDTO(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val posterUrl: String,
    val year: String,
    val genre: String,
    val country: String,

    var description: String = ""
)