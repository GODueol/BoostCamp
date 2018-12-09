package com.android.godueol.boostcamp.repository

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


class MovieAPI {

    private val mRetrofit: Retrofit
    val mAPI: API
    private val HOST = "https://openapi.naver.com/"

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        mAPI = mRetrofit.create(API::class.java)
    }
//wEPUsnDZSbPUaQ3asew0
    //ctaeIzO3TL
    companion object {
        @Volatile
        private var mInstance: MovieAPI? = null
        //SingleTon
        val instance: MovieAPI?
            get() = synchronized(lock = MovieAPI::class.java) {
                if (mInstance == null) {
                    mInstance = MovieAPI()
                }
                return mInstance
            }
    }

    interface API {
        @GET("v1/search/movie.json")
        @Headers("X-Naver-Client-Id:wEPUsnDZSbPUaQ3asew0", "X-Naver-Client-Secret:ctaeIzO3TL")
        fun getMovieList(@Query("query") str:String): Observable<Response<ResponseBody>>
    }
}