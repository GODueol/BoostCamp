package com.android.godueol.boostcamp.utlis

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.adapter.RecyclerViewAdapter
import com.android.godueol.boostcamp.model.MovieInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class DataBindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("ListItem")
        fun bindListAdapter(recyclerView: RecyclerView, itemList: List<MovieInfo>) {
            Log.d("test", itemList.toString())
            for(item in itemList){
                Log.w("item",item.toString())
            }

            if(recyclerView.layoutManager == null) {
                    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
                    recyclerView.setItemViewCacheSize(100)
                    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
            }
            if(recyclerView.adapter  == null){
                recyclerView.adapter = RecyclerViewAdapter()
            }

            (recyclerView.adapter as? RecyclerViewAdapter)?.apply {
                addItems(itemList)
            }
        }


        @JvmStatic
        @BindingAdapter("bind:imgUrl", "bind:error")
        fun bindImage(imageView: ImageView, url: String, error: Drawable) {
            val requestOption = RequestOptions()
            requestOption.error(error)

            Glide.with(imageView.context)
                .load(url)
                .apply(requestOption)
                .into(imageView)
        }
    }

}