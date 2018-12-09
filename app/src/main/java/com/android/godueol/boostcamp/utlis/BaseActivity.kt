package com.android.godueol.boostcamp.utlis

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    val rxBinder = RxBinder()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        rxBinder.apply(Event.OnCreate)
    }

    override fun onStart() {
        rxBinder.apply(Event.OnStart)
        super.onStart()
    }

    override fun onResume() {
        rxBinder.apply(Event.OnResume)
        super.onResume()
    }

    override fun onPause() {
        rxBinder.apply(Event.OnPause)
        super.onPause()
    }

    override fun onStop() {
        rxBinder.apply(Event.OnStop)
        super.onStop()
    }

    override fun onDestroy() {
        rxBinder.apply(Event.OnDestroy)
        super.onDestroy()
    }
}