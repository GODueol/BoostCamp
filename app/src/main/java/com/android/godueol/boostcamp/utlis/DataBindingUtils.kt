package com.android.godueol.boostcamp.utlis

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.R
import com.android.godueol.boostcamp.adapter.RecyclerViewAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DataBindingUtils {
    companion object {

        @JvmStatic
        @BindingAdapter("adapter")
        fun bindListAdapter(recyclerView: RecyclerView, adatapter: RecyclerViewAdapter) {
            if (recyclerView.layoutManager == null) {
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
                recyclerView.setItemViewCacheSize(100)
                val dedcoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
                recyclerView.addItemDecoration(dedcoration)
            }

            if (recyclerView.adapter == null) {
                recyclerView.adapter = adatapter
            }
        }

        @JvmStatic
        @BindingAdapter("imgUrl", "error")
        fun bindImage(imageView: AppCompatImageView, url: String, error: Drawable) {
            val requestOption = RequestOptions().placeholder(error).error(error)
            Glide.with(imageView.context)
                    .load(url)
                    .apply(requestOption)
                    .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("drawBar")
        fun drawBar(textView: AppCompatTextView, userRating: Double?) {
            userRating ?: return

            val context = textView.context
            // 10점만점 평점을 100dp 기준으로 컨버딩
            val dp = (userRating * 10).DpToPixel(context).toInt()
            // Int화 해서 비교
            val userRatingInt = (userRating * 10).toInt()
            val color = when (userRatingInt) {
                in 0..32 -> R.color.red
                in 33..65 -> R.color.yello
                in 66..100 -> R.color.green
                else -> R.color.transparent
            }

            textView.setBackgroundResource(color)
            textView.width = dp

        }


        @JvmStatic
        @BindingAdapter("onScrollStateChanged")
        fun setOnScrollListener(recyclerView: RecyclerView, scrollStateChanged: OnScrollStateChanged?) {
            if (scrollStateChanged == null) {
                return
            }

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    scrollStateChanged.onScrollStateChanged(recyclerView, newState)
                }
            })
        }

        interface OnScrollStateChanged {
            fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int)
        }
    }

}