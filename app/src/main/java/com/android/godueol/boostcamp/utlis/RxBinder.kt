package com.android.godueol.boostcamp.utlis

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class RxBinder {

    private val taskMap = HashMap<Event, CompositeDisposable>()

    fun apply(event: Event) {
        taskMap[event]?.clear()
    }

    fun bind(event: Event, task: () -> Disposable) {
        task().let {
            taskMap[event]?.add(it)
        }
    }

}