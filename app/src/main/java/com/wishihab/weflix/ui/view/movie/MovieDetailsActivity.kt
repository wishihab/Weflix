package com.wishihab.weflix.ui.view.movie

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
        val progressbar = findViewById<ProgressBar>(R.id.progress_bar)
        val txterror = findViewById<TextView>(R.id.txt_error)
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
            progressbar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txterror.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: Detail){
        val tvtitle = findViewById<TextView>(R.id.tvtitle)
        val tvtagline = findViewById<TextView>(R.id.tvtagline)
        val tvrating = findViewById<TextView>(R.id.tvrating)
        val tvoverview = findViewById<TextView>(R.id.tvoverview)
        val ivposter = findViewById<ImageView>(R.id.ivposter)

        tvtitle.text = it.title
        tvtagline.text = it.tagline
        tvrating.text = it.voteAverage.toString()
        tvoverview.text = it.overview

        val moviePosterURL = IMAGE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(ivposter)

    }

    fun bindVideo( it: Result){
        val videoKey = it.key
        val playerview = findViewById<YouTubePlayerView>(R.id.playerview)
        val ivposter = findViewById<ImageView>(R.id.ivposter)

        ivposter.setOnClickListener {
            ivposter.visibility = View.GONE
            playerview.visibility = View.VISIBLE

            playerview.getPlayerUiController().showFullscreenButton(false)
            playerview.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
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
