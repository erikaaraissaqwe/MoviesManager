package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentRegisterMovieBinding

class RegisterMovieFragment : Fragment() {


    private lateinit var fragmentRegisterMovieBinding: FragmentRegisterMovieBinding

    companion object {
        fun newInstance() = RegisterMovieFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getString(R.string.movie_register)

        return inflater.inflate(R.layout.fragment_register_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}