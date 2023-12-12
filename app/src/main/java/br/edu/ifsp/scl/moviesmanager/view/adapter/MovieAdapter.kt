package br.edu.ifsp.scl.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import com.bumptech.glide.Glide

class MovieAdapter(private var movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    var movieListener: MovieListener?=null

    private lateinit var tileMovieBinding: TileMovieBinding

    inner class MovieViewHolder(view: TileMovieBinding): RecyclerView.ViewHolder(view.root){
        var nameViewHolder = view.nameTv
        var watchedViewHolder = view.watchedTv
        var starsViewHolder = view.startsTv
        var genreViewHolder = view.generoTv
        var urlViewHolder = view.imgUrlIm

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
        holder.watchedViewHolder.text = movieList[position].watched.toString()
        holder.starsViewHolder.text = movieList[position].stars.toString()
        holder.genreViewHolder.text = movieList[position].genre
        var context = holder.urlViewHolder.context
        var urlImg = movieList[position].url
        Glide.with(context).load(urlImg).into(holder.urlViewHolder);
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

    fun setClickListener(listener: MovieListener)
    {
        this.movieListener = listener
    }

    interface MovieListener
    {
        fun onItemClick(pos: Int)
    }
}