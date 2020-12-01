package com.wishihab.weflix.data.repo


enum class Status {
    RUNNING,
    SUCCESS,
    FAILED

}

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Terjadi kesalahan")
        val ENDOFLIST: NetworkState = NetworkState(Status.FAILED, "Mencapai batas maksimal")

    }
}