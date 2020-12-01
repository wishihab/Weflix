package com.wishihab.weflix.ui.view.movie

import androidx.lifecycle.LiveData
import com.wishihab.weflix.data.movie.ApiService
import com.wishihab.weflix.data.movie.MovieDetailsNetworkDataSource
import com.wishihab.weflix.data.repo.NetworkState
import com.wishihab.weflix.ui.viewmodel.movie.Detail
import com.wishihab.weflix.ui.viewmodel.video.Result
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : ApiService) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<Detail> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun fetchVideo (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<Result> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchVideo(movieId)

        return movieDetailsNetworkDataSource.downloadedVideoResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}