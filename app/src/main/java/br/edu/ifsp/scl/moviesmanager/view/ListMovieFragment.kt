package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter

class ListMovieFragment : Fragment(){

    private lateinit var fragmentListMovieBinding: FragmentListMovieBinding

    lateinit var viewModel: MovieViewModel

    private val movieList: MutableList<Movie> = mutableListOf();


    private val movieAdapter: MovieAdapter by lazy{
        MovieAdapter(movieList)
    }

    private val navController: NavController by lazy {
        findNavController()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentListMovieBinding = FragmentListMovieBinding.inflate(inflater, container, false)

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_list)

        fragmentListMovieBinding.buttonAdd.setOnClickListener {
            navController.navigate(R.id.action_listMovieFragment_to_registerMovieFragment)
        }

        fragmentListMovieBinding.apply {
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.adapter = movieAdapter
        }

        return fragmentListMovieBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.allMovies.observe(requireActivity()){ movies ->
            movieList.clear()
            movies.forEachIndexed{
                    index, movie ->
                movieList.add(movie)
                movieAdapter.notifyItemChanged(index)
            }
        }

        viewModel.getMovies()
    }

}