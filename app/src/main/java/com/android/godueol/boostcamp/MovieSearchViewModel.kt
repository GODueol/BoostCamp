package com.android.godueol.boostcamp

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.repository.MovieAPI
import com.android.godueol.boostcamp.utlis.RxBinder
import com.android.godueol.boostcamp.utlis.threadIoToComputation
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


class MovieSearchViewModel(private val rxBinder: RxBinder) {
    var items = ObservableArrayList<MovieInfo>()
    var searchword: String? = null
    var LastWord: String? = null

    @SuppressLint("CheckResult")
    fun requestMovies(searchWord: String) {
        MovieAPI.instance!!.mAPI
            .getMovieList(URLEncoder.encode(searchWord, "UTF-8"))
            .compose(threadIoToComputation())
            .map {
                it.body()?.string()
            }
            .subscribe(
                {
                    val jsonObject = JSONObject(it)
                    val jsonArray = jsonObject.getJSONArray("items")
                    Log.d("size", "" + jsonArray.length())
                    for (i in 0 until jsonArray.length()) {
                        val item = Gson().fromJson(jsonArray[i].toString(), MovieInfo::class.java)
                        items.add(item)
                    }
                }, {
                    Log.e("fail", it.message)
                })
    }

    fun onSearch(v: View, word: String?) {
        Observable.just(word)
            .filter {
                if (!it.isNullOrEmpty() && it.length > 2) {
                    true
                } else {
                    Toast.makeText(v.context, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                    false
                }
            }
            .debounce(50, TimeUnit.MILLISECONDS)
            .subscribe {
                requestMovies(it!!)
            },
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            searchword = s.toString()
        }

        @SuppressLint("CheckResult")
        override fun afterTextChanged(s: Editable) {


            val observable = Observable.create<String> { emitter ->
                emitter.onNext(s.toString())
            }


            observable.debounce(200, TimeUnit.MILLISECONDS, Schedulers.computation())
                .filter {
                    it.isNotEmpty() && it.length > 2
                }
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribe {
                    requestMovies(it!!)
                }
        }
    }


}