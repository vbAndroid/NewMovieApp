package com.example.movielistapp.Utils


import androidx.lifecycle.MutableLiveData

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T?) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T? = content
}

/**
 * Notify Mutable Live Data
 * */
fun <T> MutableLiveData<Event<T>>.notifyObserverEvent() {
    this.postValue(Event(this.value?.peekContent()))
}

/**
 * Clear Mutable Live Data
 * */
fun <T> MutableLiveData<Event<T>>.clear() {
    this.postValue(Event(null))
}