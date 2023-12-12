package br.edu.ifsp.scl.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.WATCHED_TRUE
import com.bumptech.glide.Glide

class MovieAdapter(private var movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movieListener: MovieListener?=null

    private lateinit var tileMovieBinding: TileMovieBinding

    inner class MovieViewHolder(view: TileMovieBinding): RecyclerView.ViewHolder(view.root){
        var nameViewHolder = view.nameTv
        var watchedViewHolder = view.watchedTv
        var starsViewHolder = view.startsTv
        var genreViewHolder = view.generoTv
        var imgUrlViewHolder = view.imgUrlIm

        init {
            view.root.setOnClickListener {
                movieListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        tileMovieBinding = TileMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  MovieViewHolder(tileMovieBinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.nameViewHolder.text = movieList[position].name
        holder.watchedViewHolder.text = setWatched(movieList[position].watched)
        holder.starsViewHolder.text = setStars(movieList[position].stars)
        holder.genreViewHolder.text = movieList[position].genre
        val context = holder.imgUrlViewHolder.context
        Glide.with(context).load(movieList[position].url).into(holder.imgUrlViewHolder)
    }

    fun setClickListener(listener: MovieListener)
    {
        this.movieListener = listener
    }

    interface MovieListener
    {
        fun onItemClick(pos: Int)
    }
    private fun setStars(stars: Int): String
    {
        var star = ""
        for (x in 0 until stars){
            star += "⭐"
        }
        return star
    }

    private fun setWatched(watched: Int): String
    {
        if (watched == WATCHED_TRUE)
            return "Já vi!"

        return "Ainda quero ver"
    }

}