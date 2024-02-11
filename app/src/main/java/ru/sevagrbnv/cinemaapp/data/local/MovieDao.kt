package ru.sevagrbnv.cinemaapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(entity = MovieDbDTO::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movieDbDTO: MovieDbDTO)

    @Query("SELECT * FROM movie WHERE id = (:movieId) LIMIT 1")
    suspend fun getMovie(movieId: Long): MovieDbDTO

    @Query("SELECT * FROM movie")
    fun getMovieList(): LiveData<List<MovieDbDTO>>
}
