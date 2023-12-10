package br.edu.ifsp.scl.moviesmanager.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.controller.ListMovieViewModel
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

class ListMovieFragment : Fragment() {

    private lateinit var fragmentListMovieBinding: FragmentListMovieBinding

    private lateinit var viewModel: ListMovieViewModel

    private val movieList: MutableList<Movie> = mutableListOf();

    private val navController: NavController by lazy {
        findNavController()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_list)
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}