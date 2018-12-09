package com.android.godueol.boostcamp

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.repository.MovieAPI
import com.android.godueol.boostcamp.utlis.RxBinder
import com.android.godueol.boostcamp.utlis.threadIoToMain
import com.google.gson.Gson
import org.json.JSONObject
import java.net.URLEncoder
import android.text.Editable
import android.text.TextWatcher



class MovieSearchViewModel(private val rxBinder: RxBinder) {
    var items = ObservableArrayList<MovieInfo>()
    var search :String? = null
    @SuppressLint("CheckResult")
    fun onSearch(v: View) {
        Log.d("test", "test")
        rxBinder
        MovieAPI.instance!!.mAPI
                .getMovieList(URLEncoder.encode("starwars", "UTF-8"))
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
                    it.printStackTrace()
                    Log.e("fail", it.message)
                })
    }


    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            Log.d("beforeTextChanged",s.toString())
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            Log.d("onTextChanged",s.toString())
        }

        override fun afterTextChanged(s: Editable) {
            if(s.isNullOrBlank())
                return

            // debounce 예정
        }
    }


}