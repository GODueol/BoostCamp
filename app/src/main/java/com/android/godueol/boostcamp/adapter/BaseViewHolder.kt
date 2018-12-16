package com.android.godueol.boostcamp.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class  BaseViewHolder<T : ViewDataBinding>(itemView:View): RecyclerView.ViewHolder(itemView){
        var binding : T? = DataBindingUtil.bind(itemView)
}