package br.edu.ifsp.scl.moviesmanager.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Movie(
    @PrimaryKey
    var name: String,
    var releaseYears: String,
    var production: String,
    var minutes: Long,
    var watched: Int = WATCHED_FALSE,
    var stars: Int = INVALID_STARS,
    var genre: String,
    var url: String,

) {
    companion object {
        const val WATCHED_TRUE = 1
        const val WATCHED_FALSE = 0
        const val INVALID_STARS = -1
    }
}