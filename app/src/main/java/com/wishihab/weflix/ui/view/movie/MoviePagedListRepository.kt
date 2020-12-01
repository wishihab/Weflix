package com.wishihab.weflix.ui.view.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wishihab.weflix.data.movie.ApiService
import com.wishihab.weflix.data.movie.MovieDataSource
import com.wishihab.weflix.data.movie.MovieDataSourceFactory
import com.wishihab.weflix.data.movie.POST_PER_PAGE
import com.wishihab.weflix.data.repo.NetworkState
import com.wishihab.weflix.ui.viewmodel.movie.Result
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiServiceMovie : ApiService) {

    lateinit var moviePagedList: LiveData<PagedList<Result>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Result>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiServiceMovie, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.liveDataSource, MovieDataSource::networkState)
    }
}