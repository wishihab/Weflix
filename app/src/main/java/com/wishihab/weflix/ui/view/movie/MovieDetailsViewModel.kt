package com.wishihab.weflix.ui.view.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wishihab.weflix.data.repo.NetworkState
import com.wishihab.weflix.ui.viewmodel.movie.Detail
import com.wishihab.weflix.ui.viewmodel.video.Result
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<Detail> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable,movieId)
    }

    val videoDetails : LiveData<Result> by lazy {
        movieRepository.fetchVideo(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}