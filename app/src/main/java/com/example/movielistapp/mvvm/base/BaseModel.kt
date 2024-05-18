package com.example.movielistapp.mvvm.base

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
open class BaseModel(
    @SerializedName("success") val status: Boolean = false,
    @SerializedName("message") val message: String = "",
) : Parcelable {
    override fun toString(): String = "${javaClass.simpleName} : STATUS: $status MESSAGE: $message"

}


