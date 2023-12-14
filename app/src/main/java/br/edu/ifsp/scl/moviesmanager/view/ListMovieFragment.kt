package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter
import com.google.android.material.snackbar.Snackbar

class ListMovieFragment : Fragment(){

    private lateinit var fragmentListMovieBinding: FragmentListMovieBinding


    private val movieList: MutableList<Movie> = mutableListOf()

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory
    }

    private val movieAdapter: MovieAdapter by lazy{
        MovieAdapter(movieList)
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentListMovieBinding = FragmentListMovieBinding.inflate(inflater, container, false)


        fragmentListMovieBinding.apply {
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.adapter = movieAdapter
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_list)
            buttonAdd.setOnClickListener {
                navController.navigate(R.id.action_listMovieFragment_to_registerMovieFragment)
            }
        }

        return fragmentListMovieBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateMovieList()

        setListenerDetailsMovieView()

        initializeMenu()
    }

    private fun setListenerDetailsMovieView() {
        val movieListener = object : MovieAdapter.MovieListener {
            override fun onItemClick(pos: Int) {
                val movie = movieList[pos]

                val bundle = Bundle()
                bundle.putString("nameMovie", movie.name)

                findNavController().navigate(
                    R.id.action_listMovieFragment_to_detailsFragment,
                    bundle
                )
            }

        }
        movieAdapter.setClickListener(movieListener)
    }

    private fun updateMovieList(type: String = "") {
        viewModel.allMovies.observe(requireActivity()) { movies ->
            movieList.clear()
            movies.forEachIndexed { index, movie ->
                movieList.add(movie)
            }
            movies.let {
                this.movieAdapter.notifyDataSetChanged()
            }
        }

        when (type) {
            "orderByName" -> {
                viewModel.getMoviesOrderByName()
            }
            "orderByStars" -> {
                viewModel.getMoviesOrderByStarts()
            }
            else -> viewModel.getMovies()
        }
    }

    private fun initializeMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_ordenarName -> {
                        viewModel.getMoviesOrderByName()
                        Snackbar.make(
                            fragmentListMovieBinding.root,
                            "Filmes ordenados por nome.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        updateMovieList("orderByName")
                        true
                    }

                    R.id.action_ordenarStarts -> {
                        viewModel.getMoviesOrderByStarts()
                        Snackbar.make(
                            fragmentListMovieBinding.root,
                            "Filmes ordenados por nota.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        updateMovieList("orderByStars")
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}