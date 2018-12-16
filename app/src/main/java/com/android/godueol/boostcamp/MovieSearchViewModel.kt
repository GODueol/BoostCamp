package com.android.godueol.boostcamp

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.adapter.RecyclerViewAdapter
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.repository.MovieAPI
import com.android.godueol.boostcamp.utlis.DataBindingUtils.Companion.OnScrollStateChanged
import com.android.godueol.boostcamp.utlis.Event
import com.android.godueol.boostcamp.utlis.RxBinder
import com.android.godueol.boostcamp.utlis.threadIoToComputation
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


class MovieSearchViewModel(private val rxBinder: RxBinder) {
    var items: MutableList<MovieInfo> = ArrayList()
    var isLoading = ObservableField<Boolean>(false)
    var adapter = ObservableField<RecyclerViewAdapter>(RecyclerViewAdapter(items))
    var lastQuery: String? = null
    var debounceQuery: String? = null
    var isLast: Boolean = false
    var duplicationSet: MutableSet<String> = hashSetOf() // 중복처리를 위한 set

    fun initItem(searchWord: String) {
        // 전 검색어와 같다면 동작 x
        if (searchWord == lastQuery) {
            return
        }
        isLast = false
        adapter.get()?.clear()
        lastQuery = searchWord
    }

    fun requestMovies(searchWord: String, start: Int) {
        rxBinder.bind(Event.OnDestroy) {
            Observable.just(start)
                    .filter { start <= 1 }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        initItem(searchWord)
                    }
        }


        rxBinder.bind(Event.OnDestroy) {
            isLoading.set(true)
            val parameter = URLEncoder.encode(searchWord, "UTF-8")
            MovieAPI.instance!!.mAPI
                    .getMovieList(parameter, 30, start)
                    .compose(threadIoToComputation())
                    .map { it.body()?.string() }
                    .map { JSONObject(it).getJSONArray("items") }
                    .filter { it.length() > 0 }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                val size = it.length()
                                Log.d("size", size.toString())

                                val beforeSize = this.items.size

                                for (i in 0 until size) {
                                    val item = Gson().fromJson(it[i].toString(), MovieInfo::class.java)
                                    if(duplicationSet.contains(item.link)){
                                        isLast = true
                                    }else{
                                        duplicationSet.add(item.link!!)
                                        items.add(item)
                                    }
                                }

                                val afterSize = this.items.size
                                Log.d("size : ", beforeSize.toString() + "/" + afterSize)
                                adapter.get()?.notifyItemRangeInserted(beforeSize, afterSize)
                            },
                            { Log.e("fail", it.message) },
                            { isLoading.set(false) })
        }
    }

    fun onSearch(v: View, word: String?) {

        if (word.isNullOrEmpty()) {
            Toast.makeText(v.context, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 마지막 검색상태에서 1~1.2 사이로 머물게되면 자동검색
        rxBinder.bind(Event.OnDestroy) {
            Observable.just(word)
                    .filter { it != lastQuery }
                    .debounce(50, TimeUnit.MILLISECONDS)
                    .subscribe {
                        requestMovies(it!!, 1)
                    }
        }
    }

    var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            debounceQuery = s.toString()

            val observable =
                    Observable.create<String> { emitter ->
                        emitter.onNext(s.toString())
                    }

            // 마지막 검색상태에서 1~1.2 사이로 머물게되면 자동검색 ( 자동검색은 2자 이상부터 작동)
            rxBinder.bind(Event.OnDestroy) {
                observable
                        .debounce(200, TimeUnit.MILLISECONDS, Schedulers.computation())
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .filter { it.isNotEmpty() && it.length > 1 && it == debounceQuery && it != lastQuery }
                        .filter { !(isLoading.get() ?: false) }
                        .subscribe {
                            requestMovies(it!!, 1)
                        }
            }
        }
    }

    var scrollPaging = object : OnScrollStateChanged {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (items.size >= 1000 || isLast) {
                Toast.makeText(recyclerView.context, "더이상 검색결과를 볼 수 없습니다.", Toast.LENGTH_LONG).show()
                return
            }

            rxBinder.bind(Event.OnDestroy) {
                Observable.just(recyclerView)
                        .filter { !isLast }
                        .filter { items.size > 4 }
                        .throttleFirst(100, TimeUnit.MILLISECONDS)
                        .debounce(300, TimeUnit.MICROSECONDS)
                        .filter { !it.canScrollVertically(1) }
                        .subscribe {
                            requestMovies(lastQuery!!, items.size + 1)
                        }
            }
        }

    }
}