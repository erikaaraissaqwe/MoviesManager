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
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_FALSE
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE
import com.google.android.material.snackbar.Snackbar

class RegisterMovieFragment : Fragment() {


    private lateinit var fragmentRegisterMovieBinding: FragmentRegisterMovieBinding

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRegisterMovieBinding = FragmentRegisterMovieBinding.inflate(inflater, container, false)

        fragmentRegisterMovieBinding.apply {
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_register)
            fragmentRegisterMovieBinding.commonLayout.urlImg.visibility = View.GONE
        }

        return fragmentRegisterMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        var commonLayout = fragmentRegisterMovieBinding.commonLayout

        fragmentRegisterMovieBinding.commonLayout.saveBt.setOnClickListener {
            if (validate()){
                var name = commonLayout.editTextName.text.toString()
                var releaseYears = commonLayout.editTextReleaseYears.text.toString()
                var production = commonLayout.editTextProduction.text.toString()
                var minutes = commonLayout.editTextMinutes.text.toString().toLong()
                var watched = if (commonLayout.watchedCb.isChecked) WATCHED_TRUE else WATCHED_FALSE
                var stars = commonLayout.editTextStars.text.toString().toInt()
                var genre = (commonLayout.editSpinnerGenre.selectedView as TextView).text.toString()
                var url = commonLayout.editTexturlImg.text.toString()

                val movie = Movie(name, releaseYears, production, minutes, watched, stars, genre, url)

                viewModel.createMovie(movie)
                Snackbar.make(fragmentRegisterMovieBinding.root, "Filme inserido", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            else
                Toast.makeText(commonLayout.editTextName.context, "Complete todos os campos", Toast.LENGTH_SHORT).show()

        }
    }

    fun validate(): Boolean {
        if (fragmentRegisterMovieBinding.commonLayout.editTextName.text.isEmpty())
            return false
        return true
    }

}