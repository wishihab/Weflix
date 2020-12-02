package com.wishihab.weflix.ui.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wishihab.weflix.R
import com.wishihab.weflix.data.movie.ApiService
import com.wishihab.weflix.data.movie.Client
import com.wishihab.weflix.data.repo.NetworkState
import com.wishihab.weflix.ui.view.movie.MoviePagedListAdapter
import com.wishihab.weflix.ui.view.movie.MoviePagedListRepository

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeActivityViewModel
    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val apiServiceMovie : ApiService = Client.getClient()

        movieRepository = MoviePagedListRepository(apiServiceMovie)

        viewModel = getViewModel()

        val recycleMovie = findViewById<RecyclerView>(R.id.recycleMovie)
        val movieAdapter = MoviePagedListAdapter(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtError = findViewById<TextView>(R.id.txtError)
        val gridLayoutManager = GridLayoutManager(this, 1)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1
                else return 2
            }
        };


        recycleMovie.layoutManager = gridLayoutManager
        recycleMovie.setHasFixedSize(true)
        recycleMovie.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }


    private fun getViewModel(): HomeActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeActivityViewModel(movieRepository) as T
            }
        })[HomeActivityViewModel::class.java]
    }

}
