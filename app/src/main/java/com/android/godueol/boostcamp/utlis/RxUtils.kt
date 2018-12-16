package com.android.godueol.boostcamp.utlis

import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers

fun <T>threadIoToComputation(): ObservableTransformer<T, T>{
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }
}