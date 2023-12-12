package br.edu.ifsp.scl.moviesmanager.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Dao
interface MovieDAO {

    companion object {
        const val MOVIE_TABLE = "movie"
    }
    @Insert
    fun insert(movie: Movie)

    @Update
    fun update (movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE name=:name")
    fun getMovieByName(name: String): Movie
}