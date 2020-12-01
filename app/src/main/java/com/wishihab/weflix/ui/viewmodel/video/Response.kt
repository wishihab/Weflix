package com.wishihab.weflix.ui.viewmodel.video

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)