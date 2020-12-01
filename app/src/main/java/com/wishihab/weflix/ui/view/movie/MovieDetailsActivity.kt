package com.wishihab.weflix.ui.view.movie

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.wishihab.weflix.BuildConfig.IMAGE_URL
import com.wishihab.weflix.R
import com.wishihab.weflix.data.movie.ApiService
import com.wishihab.weflix.data.movie.Client
import com.wishihab.weflix.data.repo.NetworkState
import com.wishihab.weflix.ui.viewmodel.movie.Detail
import com.wishihab.weflix.ui.viewmodel.video.Result

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : ApiService = Client.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, androidx.lifecycle.Observer {
            bindUI(it)
        })

        viewModel.videoDetails.observe(this, androidx.lifecycle.Observer {
            bindVideo(it)
        })

        viewModel.networkState.observe(this, androidx.lifecycle.Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: Detail){
        tv_title.text = it.title
        tv_tag_line.text = it.tagline
        tv_rating.text = it.voteAverage.toString()
        tv_overview.text = it.overview

        val moviePosterURL = IMAGE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_poster)

    }

    fun bindVideo( it: Result){
        val videoKey = it.key
        layout_play.setOnClickListener {
            layout_play.visibility = View.GONE
            player_view.visibility = View.VISIBLE

            player_view.getPlayerUiController().showFullscreenButton(false)
            player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoKey, 0f)
                }
            })

        }

    }


    private fun getViewModel(movieId:Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository,movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }
}
