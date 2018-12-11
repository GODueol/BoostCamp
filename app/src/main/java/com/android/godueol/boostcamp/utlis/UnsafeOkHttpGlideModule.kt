package com.android.godueol.boostcamp.utlis

import android.content.Context
import androidx.annotation.NonNull
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun registerComponents(@NonNull context: Context, @NonNull glide: Glide, @NonNull registry: Registry) {
        val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}
