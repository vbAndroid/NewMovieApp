package com.example.movielistapp.Utils


import androidx.annotation.Keep

@Keep
data class LoadingState constructor(val status: Status, val msg: String? = "") {
    companion object {
        val IDLE = LoadingState(Status.IDLE)
        val LOADED = LoadingState(Status.SUCCESS)
        fun loading(msg: String?) = LoadingState(Status.RUNNING, msg)
        fun error(msg: String?) = LoadingState(Status.FAILED, msg)
    }

    enum class Status {
        IDLE,
        RUNNING,
        SUCCESS,
        FAILED
    }
}