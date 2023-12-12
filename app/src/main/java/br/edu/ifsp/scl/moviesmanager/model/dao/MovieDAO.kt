package br.edu.ifsp.scl.moviesmanager.model.dao

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
    fun getAllMovies(): MutableList<Movie>

    @Query("SELECT * FROM $MOVIE_TABLE WHERE name=:name")
    fun getMovieByName(name: String): Movie

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY name")
    fun getAllMoviesOrderByName(): MutableList<Movie>

    @Query("SELECT * FROM $MOVIE_TABLE ORDER BY stars DESC")
    fun getAllMoviesOrderByStarts(): MutableList<Movie>
}