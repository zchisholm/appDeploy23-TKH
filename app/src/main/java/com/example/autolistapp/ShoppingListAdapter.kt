package com.example.autolistapp

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

// Define an interface for click events
interface OnItemClickListener {
    fun onItemClick(item: ShoppingItem)
}

class ShoppingListAdapter(
    private val items: MutableList<ShoppingItem>, // Change this to MutableList
    private val listener: OnItemClickListener // Pass the listener to the adapter
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    // Define the ViewHolder class inside the Adapter class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.tvItemName)
        val itemCheckbox: CheckBox = view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemName.text = currentItem.name
        holder.itemCheckbox.isChecked = currentItem.isBought

        // Set an OnClickListener on the itemView
        holder.itemView.setOnClickListener {
            listener.onItemClick(currentItem)
        }
    }

    override fun getItemCount() = items.size

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
}
