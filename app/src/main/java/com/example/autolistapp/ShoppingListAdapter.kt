package com.example.autolistapp

import android.view.GestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(
    private val items: MutableList<ShoppingItem>
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.tvItemName)

        val gestureDetector = GestureDetector(view.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                itemView.performClick()  // Optional: Mimic click behavior on double tap
                return true
            }
        })

        init {
            view.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemName.text = currentItem.name
        holder.itemName.paint.isStrikeThruText = currentItem.isCrossedOut

        // Respond to double tap using the ViewHolder's GestureDetector
        holder.itemView.setOnClickListener {
            currentItem.isCrossedOut = !currentItem.isCrossedOut
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = items.size

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: ShoppingItem, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }
}
