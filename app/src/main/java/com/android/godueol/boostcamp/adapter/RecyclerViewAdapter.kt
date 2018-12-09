package com.android.godueol.boostcamp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.model.MovieInfo
import com.android.godueol.boostcamp.R
import com.android.godueol.boostcamp.databinding.MovieListRowBinding

class RecyclerViewAdapter : RecyclerView.Adapter<BaseViewHolder<MovieListRowBinding>>() {

    var itemList : List<MovieInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieListRowBinding> {
      return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row,parent,false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieListRowBinding>, position: Int) {
        holder.binding?.item = itemList[position]
    }


    override fun getItemCount(): Int {
      return itemList.size
    }

}