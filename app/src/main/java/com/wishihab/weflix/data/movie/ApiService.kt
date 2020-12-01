package com.wishihab.weflix.data.movie

import com.wishihab.weflix.ui.viewmodel.movie.Detail
import com.wishihab.weflix.ui.viewmodel.movie.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovieList(@Query("page") page: Int): Single<Response>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<Detail>

    @GET("movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") id: Int): Single<com.wishihab.weflix.ui.viewmodel.video.Response>

}