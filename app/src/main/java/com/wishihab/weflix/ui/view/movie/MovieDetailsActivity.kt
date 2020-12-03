package com.wishihab.weflix.ui.view.movie

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
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
import com.wishihab.weflix.utils.movie.share
import kotlinx.android.synthetic.main.movie_detail_activity.*

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
            infoyoutubeplayer.visibility = if (it == NetworkState.LOADED) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: Detail){

        tvtitle.text = it.title
        tvtagline.text = it.tagline
        tvrating.text = it.voteAverage.toString()
        tvoverview.text = it.overview
        runtime.text = it.runtime.toString() + " Minutes"

        val moviePosterURL = IMAGE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(ivposter)

    }

    fun bindVideo( it: Result){
        val videoKey = it.key

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
    //next add to toolbar - sementara belum
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_share -> {
                share(this, "movie", viewModel.toString())
            }
        }
        return super.onOptionsItemSelected(item)
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
