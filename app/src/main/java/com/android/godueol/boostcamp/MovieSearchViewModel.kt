package com.android.godueol.boostcamp

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.repository.MovieAPI
import com.android.godueol.boostcamp.utlis.RxBinder
import com.android.godueol.boostcamp.utlis.threadIoToMain
import com.google.gson.Gson
import org.json.JSONObject
import java.net.URLEncoder
import android.text.Editable
import android.text.TextWatcher
import android.content.Intent
import android.net.Uri
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.security.auth.Subject
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MovieSearchViewModel(private val rxBinder: RxBinder) {
    var items = ObservableArrayList<MovieInfo>()
    var searchword : String? = null

    @SuppressLint("CheckResult")
    fun requestMovies(searchWord:String){
        MovieAPI.instance!!.mAPI
            .getMovieList(URLEncoder.encode(searchWord, "UTF-8"))
            .compose(threadIoToMain())
            .map {
                it.body()?.string()
            }
            .subscribe(
                {
                    val jsonObject = JSONObject(it)
                    val jsonArray = jsonObject.getJSONArray("items")
                    Log.d("size",""+jsonArray.length())
                    for (i in 0 until jsonArray.length()) {
                        val item = Gson().fromJson(jsonArray[i].toString(), MovieInfo::class.java)
                        items.add(item)
                    }
                }, {
                    Log.e("fail", it.message)
                })
    }
    fun onSearch(v: View,word:String) {
        requestMovies(word)
    }

    fun goUrl(v:View, url:String){
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(url)
        intent.data = uri
        v.context.startActivity(intent)
    }
    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
           searchword = s.toString()
        }

        @SuppressLint("CheckResult")
        override fun afterTextChanged(s: Editable) {

            if(s.isBlank()||s.length<2)
                return

            Observable.create(ObservableOnSubscribe<String> { emitter ->
                emitter.onNext(s.toString()) // skip
            }).debounce(1500, TimeUnit.MILLISECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    items.clear()
                    requestMovies(it)
                }
        }
    }


}