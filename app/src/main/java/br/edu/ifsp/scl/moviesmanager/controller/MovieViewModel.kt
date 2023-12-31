package br.edu.ifsp.scl.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val dao = MovieDatabase.getDatabase(application).movieDAO()
    val allMovies = MutableLiveData<MutableList<Movie>>()
    lateinit var movie: Movie

    fun createMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(movie)
        }
    }

    fun removeMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(movie)
        }
    }

    fun getMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = dao.getAllMovies()
            allMovies.postValue(movies)
        }
    }

    fun getMovie(name: String){
        CoroutineScope(Dispatchers.IO).launch {
            movie = dao.getMovieByName(name)
        }
    }

    fun editMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            dao.update(movie)
        }
    }

    fun getMoviesOrderByName(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = dao.getAllMoviesOrderByName()
            allMovies.postValue(movies)
        }
    }

    fun getMoviesOrderByStarts(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = dao.getAllMoviesOrderByStarts()
            allMovies.postValue(movies)
        }
    }

    companion object {
        val MovieViewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return  MovieViewModel(application) as T
            }
        }
    }

}