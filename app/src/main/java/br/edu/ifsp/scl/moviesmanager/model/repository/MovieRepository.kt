package br.edu.ifsp.scl.moviesmanager.model.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.moviesmanager.model.dao.MovieDAO
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

class MovieRepository(private val movieDAO: MovieDAO) {
    fun insert(movie: Movie){
        movieDAO.insert(movie)
    }

    fun update(movie: Movie){
        movieDAO.update(movie)
    }

    fun delete(movie: Movie){
        movieDAO.delete(movie)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieDAO.getAllMovies()
    }

    fun getMovieByName(name: String): LiveData<Movie>{
        return movieDAO.getMovieByName(name)
    }

}