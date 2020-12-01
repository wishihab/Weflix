package com.wishihab.weflix.ui.viewmodel.movie

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)