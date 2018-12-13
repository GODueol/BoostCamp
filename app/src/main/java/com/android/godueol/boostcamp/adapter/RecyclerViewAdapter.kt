package com.android.godueol.boostcamp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.godueol.boostcamp.R
import com.android.godueol.boostcamp.databinding.MovieListRowBinding
import com.android.godueol.boostcamp.model.MovieInfo

class RecyclerViewAdapter : RecyclerView.Adapter<BaseViewHolder<MovieListRowBinding>>() {

    private var itemList: MutableList<MovieInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieListRowBinding> {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MovieListRowBinding>, position: Int) {
        holder.binding?.item = itemList[position]
        holder.binding?.itemLayout?.setOnClickListener { v ->
            Intent(Intent.ACTION_VIEW).let {
                it.data = Uri.parse(itemList[position].link)
                v.context.startActivity(it)
            }
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItems(itemList: List<MovieInfo>) {
        val beforeSize = this.itemList.size
        this.itemList.addAll(itemList)
        notifyItemInserted(beforeSize)
    }

    fun clear(){
        val size = itemList.size
        itemList.clear()
       notifyItemRangeRemoved(0,size)
    }


}