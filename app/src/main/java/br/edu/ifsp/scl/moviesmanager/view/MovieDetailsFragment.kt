package br.edu.ifsp.scl.moviesmanager.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentMovieDetailsBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_FALSE
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.lang.Thread.sleep

class MovieDetailsFragment : Fragment() {

    private lateinit var fragmentMovieDetailsBinding: FragmentMovieDetailsBinding

    private lateinit var viewModel: MovieViewModel

    private lateinit var movieAdapter: MovieAdapter

    lateinit var movie: Movie

    lateinit var nameEditText: EditText
    lateinit var releaseYearsEditText: EditText
    lateinit var productionEditText: EditText
    lateinit var minutesEditText: EditText
    lateinit var watchedEditText: CheckBox
    lateinit var starsEditText: EditText
    lateinit var genreEditText: Spinner
    lateinit var urlImageView: ImageView
    lateinit var urlEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMovieDetailsBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        fragmentMovieDetailsBinding.apply {
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.details_movie_view)
            fragmentMovieDetailsBinding.commonLayout.editTextName.isEnabled= false
            movieDetailsView(false)
        }

        return fragmentMovieDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        layoutFieldInitializer()

        val nameMovie = requireArguments().getString("nameMovie")

        if (nameMovie != null) {
            viewModel.getMovie(nameMovie)
            sleep(200)
            this.movie = viewModel.movie

            nameEditText.setText(movie.name)
            releaseYearsEditText.setText(movie.releaseYears)
            productionEditText.setText(movie.production)
            minutesEditText.setText(movie.minutes.toString())
            watchedEditText.isChecked = isWatchedChecked()
            starsEditText.setText(movie.stars.toString())
            genreEditText.setSelection(getGenrePosition(movie.genre))
            var context = urlImageView.context
            Glide.with(context).load(movie.url).into(urlImageView)
            urlEditText.setText(movie.url)
        }

        inicializeMenu()
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun layoutFieldInitializer() {
        this.nameEditText = fragmentMovieDetailsBinding.commonLayout.editTextName
        this.releaseYearsEditText = fragmentMovieDetailsBinding.commonLayout.editTextReleaseYears
        this.productionEditText = fragmentMovieDetailsBinding.commonLayout.editTextProduction
        this.minutesEditText = fragmentMovieDetailsBinding.commonLayout.editTextMinutes
        this.watchedEditText = fragmentMovieDetailsBinding.commonLayout.watchedCb
        this.starsEditText = fragmentMovieDetailsBinding.commonLayout.editTextStars
        this.genreEditText = fragmentMovieDetailsBinding.commonLayout.editSpinnerGenre
        this.urlEditText = fragmentMovieDetailsBinding.commonLayout.editTexturlImg
        this.urlImageView = fragmentMovieDetailsBinding.commonLayout.urlImg
    }

    private fun movieDetailsView(disabled: Boolean) {
        fragmentMovieDetailsBinding.commonLayout.saveBt.visibility = if (disabled) View.VISIBLE  else View.GONE
        fragmentMovieDetailsBinding.commonLayout.editTextReleaseYears.isEnabled = disabled
        fragmentMovieDetailsBinding.commonLayout.editTextProduction.isEnabled = disabled
        fragmentMovieDetailsBinding.commonLayout.editTextMinutes.isEnabled = disabled
        fragmentMovieDetailsBinding.commonLayout.watchedCb.isEnabled = disabled
        fragmentMovieDetailsBinding.commonLayout.editTextStars.isEnabled = disabled
        fragmentMovieDetailsBinding.commonLayout.editSpinnerGenre.isEnabled = disabled
        fragmentMovieDetailsBinding.commonLayout.editTexturlImg.isEnabled = disabled

    }
    private fun getGenrePosition(genre: String): Int {
        val genresArray = resources.getStringArray(R.array.genres)
        return genresArray.indexOf(genre)
    }

    private fun isWatchedChecked(): Boolean {
        return movie.watched == WATCHED_TRUE
    }

    fun validate(): Boolean {
        if (fragmentMovieDetailsBinding.commonLayout.editTextReleaseYears.text.isEmpty())
            return false
        return true
    }

    fun updateMovie() {
        if (validate()) {
            movie.releaseYears = releaseYearsEditText.text.toString()
            movie.production = productionEditText.text.toString()
            movie.minutes = minutesEditText.text.toString().toLong()
            movie.watched = if (watchedEditText.isChecked) WATCHED_TRUE else WATCHED_FALSE
            movie.stars = starsEditText.text.toString().toInt()
            movie.genre = (genreEditText.selectedView as TextView).text.toString()
            movie.url = urlEditText.text.toString()

            viewModel.editMovie(movie)
            sleep(200)
            movie = viewModel.movie
            Snackbar.make(fragmentMovieDetailsBinding.root, "Filme editado com sucesso.", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(
                fragmentMovieDetailsBinding.commonLayout.editTextMinutes.context,
                "Complete todos os campos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun inicializeMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.details_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_deleteMovie -> {
                        viewModel.removeMovie(viewModel.movie)
                        Snackbar.make(
                            fragmentMovieDetailsBinding.root,
                            "Filme excluido.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                        true
                    }

                    R.id.action_updateMovie -> {
                        movieDetailsView(true)
                        fragmentMovieDetailsBinding.commonLayout.saveBt.setOnClickListener {
                            updateMovie()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}