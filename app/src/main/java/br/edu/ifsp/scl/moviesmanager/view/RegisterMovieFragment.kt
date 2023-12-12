package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.controller.MovieViewModel
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentRegisterMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.INVALID_STARS
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_FALSE
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE
import com.google.android.material.snackbar.Snackbar

class RegisterMovieFragment : Fragment() {


    private lateinit var fragmentRegisterMovieBinding: FragmentRegisterMovieBinding

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentRegisterMovieBinding = FragmentRegisterMovieBinding.inflate(inflater, container, false)

        fragmentRegisterMovieBinding.apply {
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_register)
            fragmentRegisterMovieBinding.commonLayout.urlImg.visibility = View.GONE
        }

        return fragmentRegisterMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        val commonLayout = fragmentRegisterMovieBinding.commonLayout

        fragmentRegisterMovieBinding.commonLayout.saveBt.setOnClickListener {
            if (validate()){
                val name = commonLayout.editTextName.text.toString()
                val releaseYears = commonLayout.editTextReleaseYears.text.toString()
                val production = commonLayout.editTextProduction.text.toString()
                val minutes = commonLayout.editTextMinutes.text.toString().toLong()
                val watched = if (commonLayout.watchedCb.isChecked) WATCHED_TRUE else WATCHED_FALSE
                val stars = if (commonLayout.editTextStars.text.toString() != "") commonLayout.editTextStars.text.toString().toInt() else INVALID_STARS
                val genre = (commonLayout.editSpinnerGenre.selectedView as TextView).text.toString()
                val url = commonLayout.editTexturlImg.text.toString()

                val movie = Movie(name, releaseYears, production, minutes, watched, stars, genre, url)

                viewModel.createMovie(movie)
                Snackbar.make(fragmentRegisterMovieBinding.root, "Filme inserido", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else
                Toast.makeText(commonLayout.editTextName.context, "Complete todos os campos com valores válidos", Toast.LENGTH_SHORT).show()

        }
    }

    private fun validate(): Boolean {
        if (
            fragmentRegisterMovieBinding.commonLayout.editTextName.text.isEmpty() ||
            fragmentRegisterMovieBinding.commonLayout.editTextReleaseYears.text.isEmpty() ||
            fragmentRegisterMovieBinding.commonLayout.editTextMinutes.text.isEmpty() ||
            fragmentRegisterMovieBinding.commonLayout.editTextProduction.text.isEmpty() ||
            fragmentRegisterMovieBinding.commonLayout.editTexturlImg.text.isEmpty() ||
            (fragmentRegisterMovieBinding.commonLayout.editTextStars.text.isNotEmpty() && fragmentRegisterMovieBinding.commonLayout.editTextStars.text.toString().toInt() > 10)
            )
            return false
        return true
    }

}