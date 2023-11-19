package com.example.autolistapp

import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(
    private val shoppingList: MutableList<ShoppingItem>,
    private val onItemSingleTap: (ShoppingItem) -> Unit,
    private val onItemDoubleTap: (ShoppingItem) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.tvItemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shoppingList[position]
        holder.itemName.text = currentItem.name
        holder.itemView.isSelected = currentItem.isCrossedOut // Use isSelected to manage crossed-out state

        val gestureDetector = GestureDetector(holder.itemView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                onItemSingleTap(currentItem)
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                onItemDoubleTap(currentItem)
                return true
            }
        })

        holder.itemView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
    }

    override fun getItemCount(): Int {
        Log.d("ShoppingListAdapter", "Item count: ${shoppingList.size}")
        return shoppingList.size
    }


    fun addItem(item: ShoppingItem) {
        shoppingList.add(item)
        notifyItemInserted(shoppingList.size - 1)
    }

    fun removeItem(position: Int) {
        shoppingList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: ShoppingItem, position: Int) {
        shoppingList.add(position, item)
        notifyItemInserted(position)
    }
}
