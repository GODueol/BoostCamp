package com.android.godueol.boostcamp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.R
import com.android.godueol.boostcamp.databinding.MovieListRowBinding

class RecyclerViewAdapter : RecyclerView.Adapter<BaseViewHolder<MovieListRowBinding>>() {

    private var itemList : MutableList<MovieInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieListRowBinding> {
      return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieListRowBinding>, position: Int) {
        holder.binding?.item = itemList[position]
    }


    override fun getItemCount(): Int {
      return itemList.size
    }

    fun addItems(itemList : List<MovieInfo>){
        val beforeSize = this.itemList.size
        Log.d("size : ",""+beforeSize)
        this.itemList.addAll(itemList)
        Log.d("size2 : ",""+itemCount)
        notifyItemInserted(beforeSize)
    }


}