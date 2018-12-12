package com.android.godueol.boostcamp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieInfo {

    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
        get() {
            return "제목 : " + if (field.isNullOrEmpty()) {
                "정보 없음"
            } else {
                field
            }
        }
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("userRating")
    @Expose
    var userRating: Double? = 0.0
    @SerializedName("pubDate")
    @Expose
    var pubDate: String? = null
        get() {
            return "제작 : " + if (field.isNullOrEmpty()) {
                "정보 없음"
            } else {
                field
            }
        }
    @SerializedName("director")
    @Expose
    var director: String? = null
        get() {
            return "감독 : " + if (field.isNullOrEmpty()) {
                "정보 없음"
            } else {
                field
            }
        }
    @SerializedName("actor")
    @Expose
    var actor: String? = null
        get() {
            return "배우 : " + if (field.isNullOrEmpty()) {
                "정보 없음"
            } else {
                field
            }
        }
}