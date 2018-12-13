package com.android.godueol.boostcamp

import android.annotation.SuppressLint
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.repository.MovieAPI
import com.android.godueol.boostcamp.utlis.RxBinder
import com.android.godueol.boostcamp.utlis.threadIoToComputation
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableFilter
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager


class MovieSearchViewModel(private val rxBinder: RxBinder) {
    var items = ObservableArrayList<MovieInfo>()
    var isLoading = ObservableField<Boolean>(false)
    var searchWord: String? = null
    var lastSearchWord: String? = null
    var lastWord: String? = null

    @SuppressLint("CheckResult")
    fun requestMovies(searchWord: String) {
        // 전 검색어와 같다면 동작 x
        if (searchWord == lastSearchWord) {
            return
        }
        lastSearchWord = searchWord
        lastWord = searchWord
        isLoading.set(true)

        MovieAPI.instance!!.mAPI
            .getMovieList(URLEncoder.encode(searchWord, "UTF-8"))
            .compose(threadIoToComputation())
            .map { it.body()?.string() }
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
                },{
                    isLoading.set(false)
                })
    }

    @SuppressLint("CheckResult")
    fun onSearch(v: View, word: String?) {
        if (word.isNullOrEmpty() || word!!.length < 2) {
            Toast.makeText(v.context, "검색어를 2자 이상 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        Observable.just(word)
            .filter { it != lastWord }
            .debounce(50, TimeUnit.MILLISECONDS)
            .subscribe {
                requestMovies(it!!)
            }
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            searchWord = s.toString()
        }

        @SuppressLint("CheckResult")
        override fun afterTextChanged(s: Editable) {
            lastWord = s.toString()

            val observable =
                Observable.create<String> { emitter ->
                    emitter.onNext(s.toString())
                }

            observable
                .debounce(200, TimeUnit.MILLISECONDS, Schedulers.computation())
                .delay(1000, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() && it.length > 2 && it == lastWord }
                .filter{ !(isLoading.get()?:false) }
                .subscribe {
                    requestMovies(it!!)
                }
        }
    }

    fun onTouch (v:View, event:MotionEvent) : Boolean{
        return false
    }


}