package com.freegang.testat.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.freegang.androidtemplate.base.recycler.BaseAdapter
import com.freegang.testat.R
import com.freegang.testat.recycler.bean.ItemBean

class ItemAdapter(private var itemList: MutableList<ItemBean>) :
    BaseAdapter<ItemBean, ItemAdapter.ViewHolder>(itemList) {

    override fun callCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        return ViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun callBindViewHolder(holder: ViewHolder, item: ItemBean, position: Int) {
        holder.textView.text = item.title
    }

    inner class ViewHolder(view: View) : BaseAdapter.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)

        override fun init() {

        }
    }
}