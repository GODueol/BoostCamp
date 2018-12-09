package com.android.godueol.boostcamp.utlis

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.adapter.RecyclerViewAdapter
import com.android.godueol.boostcamp.model.MovieInfo
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions





class DataBindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("ListItem")
        fun bindListAdapter(recyclerView: RecyclerView, itemList: List<MovieInfo>) {
            Log.d("test",itemList.toString())

            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = RecyclerViewAdapter()

            recyclerView.setItemViewCacheSize(100)
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

            (recyclerView.adapter as? RecyclerViewAdapter).let {
                it?.itemList = itemList
            }
        }



        @JvmStatic
        @BindingAdapter("bind:imgUrl","bind:error")
        fun bindImage(imageView: ImageView, url:String, error:Drawable){

            Glide.with(imageView.context)
                    .load(url)
                    .into(imageView)
                    .onLoadFailed(error)
        }
    }

}