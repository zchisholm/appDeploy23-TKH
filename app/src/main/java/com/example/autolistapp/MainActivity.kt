package com.example.autolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast




class MainActivity : AppCompatActivity(), OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Assuming you have a list of items
        val shoppingList = listOf(ShoppingItem("Apples", 5), ShoppingItem("Bread", 1))
        // Pass 'this' as the listener
        recyclerView.adapter = ShoppingListAdapter(shoppingList, this)
    }

    // Implement the onItemClick method
    override fun onItemClick(item: ShoppingItem) {
        // Handle the item click event here
        // For example, you can show a Toast with the item name
        Toast.makeText(this, "Clicked on ${item.name}", Toast.LENGTH_SHORT).show()
    }
}
