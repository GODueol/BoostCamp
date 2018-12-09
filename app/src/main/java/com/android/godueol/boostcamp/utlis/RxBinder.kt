package com.android.godueol.boostcamp.utlis

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class RxBinder {

    val taskMap = HashMap<Event, CompositeDisposable>()

    fun apply(event: Event) {
        taskMap[event]?.clear()
    }

    fun Disposable.add(event: Event, task: () -> Disposable) {
        taskMap[event]?.add(task())
    }

}