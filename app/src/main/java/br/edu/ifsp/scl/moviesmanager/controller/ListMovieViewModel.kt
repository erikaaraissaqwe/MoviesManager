package br.edu.ifsp.scl.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListMovieViewModel(application: Application) : ViewModel() {
    private val movieDaoImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieDatabase::class.java,
        MovieDatabase.MOVIE_DATABASE
    ).build().getTaskDao()

    val moviesMld = MutableLiveData<List<Movie>>()

    fun createMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.insert(movie)
        }
    }

    fun removeMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.delete(movie)
        }
    }

    fun getMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.getAllMovies()
        }
    }

    fun getMovie(name: String){
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.getMovieById(name)
        }
    }

    fun editMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.update(movie)
        }
    }
}