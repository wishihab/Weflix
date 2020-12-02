package com.wishihab.weflix.data.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wishihab.weflix.data.repo.NetworkState
import com.wishihab.weflix.ui.viewmodel.movie.Detail
import com.wishihab.weflix.ui.viewmodel.video.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetworkDataSource (private val apiServiceMovie : ApiService, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse =  MutableLiveData<Detail>()
    val downloadedMovieResponse: LiveData<Detail>
        get() = _downloadedMovieDetailsResponse

    private val _downloadedVideoResponse =  MutableLiveData<Result>()
    val downloadedVideoResponse: LiveData<Result>
        get() = _downloadedVideoResponse

    fun fetchMovieDetails(movieId: Int) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiServiceMovie.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                        }
                    )
            )

        }

        catch (e: Exception){
        }
    }

    fun fetchVideo(movieId: Int) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiServiceMovie.getVideo(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedVideoResponse.postValue(it.results[0])
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                        }
                    )
            )

        }

        catch (e: Exception){
        }
    }
}