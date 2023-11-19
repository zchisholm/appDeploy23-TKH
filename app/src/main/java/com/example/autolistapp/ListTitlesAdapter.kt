package com.example.autolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListTitlesAdapter(
    private val lists: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ListTitlesAdapter.ListTitleViewHolder>() {

    class ListTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTitleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_title_item, parent, false)
        return ListTitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListTitleViewHolder, position: Int) {
        val listTitle = lists[position]
        holder.titleTextView.text = listTitle
        holder.itemView.setOnClickListener { onClick(listTitle) }
    }

    override fun getItemCount(): Int = lists.size
}
