package com.wishihab.weflix.data.repo


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Berhasil")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Berjalan")
        val ENDOFLIST: NetworkState = NetworkState(Status.FAILED, "Mencapai batas maksimal")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Terjadi kesalahan")
    }
}