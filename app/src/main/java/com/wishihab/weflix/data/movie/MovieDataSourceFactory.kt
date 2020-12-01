package com.wishihab.weflix.data.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.wishihab.weflix.ui.viewmodel.movie.Result
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiServiceMovie : ApiService, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Result>() {

    val liveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Result> {
        val movieDataSource = MovieDataSource(apiServiceMovie,compositeDisposable)

        liveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}