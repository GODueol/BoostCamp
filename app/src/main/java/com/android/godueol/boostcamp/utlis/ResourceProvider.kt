package com.android.godueol.boostcamp.utlis

import android.content.Context

class ResourceProvider(val context: Context){

    fun String(regId :Int) :String = context.resources.getString(regId)

}