package br.edu.ifsp.scl.moviesmanager.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.model.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MovieRepository
    var allMovies : LiveData<List<Movie>>
    lateinit var movie : LiveData<Movie>

    init {
        val dao = MovieDatabase.getDatabase(application).movieDAO()
        repository = MovieRepository(dao)
        allMovies = repository.getAllMovies()
    }

    fun createMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(movie)
        }
    }

    fun removeMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(movie)
        }
    }

    fun getMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllMovies()
        }
    }

    fun getMovie(name: String){
        CoroutineScope(Dispatchers.IO).launch {
            movie = repository.getMovieByName(name)
        }
    }

    fun editMovie(movie: Movie){
        CoroutineScope(Dispatchers.IO).launch {
            repository.update(movie)
        }
    }

    /*companion object{
        val MovieViewModelFactory = object: ViewModelProvider.Factory{
            override fun <T: ViewModel> create(modelClass: Class<T>, extras: CreationExtras):T {
                MovieViewModel(checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])) as T
                return super.create(modelClass, extras)
            }
        }
    }*/
}