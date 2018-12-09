package com.android.godueol.boostcamp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieInfo(
        @SerializedName("image")
        @Expose
        var image: String,
        @SerializedName("title")
        @Expose
        var title: String,
        @SerializedName("link")
        @Expose
        var link: String,
        @SerializedName("userRating")
        @Expose
        var userRating: Double,
        @SerializedName("pubDate")
        @Expose
        var pubDate: String,
        @SerializedName("director")
        @Expose
        var director: String,
        @SerializedName("actor")
        @Expose
        var actor: String)