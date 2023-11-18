package com.example.autolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var adapter: ShoppingListAdapter
    private val shoppingList = mutableListOf(ShoppingItem("Apples", 5), ShoppingItem("Bread", 1))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShoppingListAdapter(shoppingList, this)
        recyclerView.adapter = ShoppingListAdapter(shoppingList, this)

        val fab: FloatingActionButton = findViewById(R.id.fab_add_item)
        fab.setOnClickListener {
            showAddItemDialog()
        }
    }

    // Implement the onItemClick method
    override fun onItemClick(item: ShoppingItem) {
        Toast.makeText(this, "Clicked on ${item.name}", Toast.LENGTH_SHORT).show()
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val editTextItemName = dialogView.findViewById<EditText>(R.id.editTextItemName)
        val editTextItemQuantity = dialogView.findViewById<EditText>(R.id.editTextItemQuantity)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add New Item")
            .setPositiveButton("Add") { dialog, _ ->
                val itemName = editTextItemName.text.toString().trim()
                val itemQuantity = editTextItemQuantity.text.toString().toIntOrNull() ?: -1

                if (validateInput(itemName, itemQuantity)) {
                    addItemToList(itemName, itemQuantity)
                    updateEmptyStateView()
                    dialog.dismiss()
                } else {
                    // Show an error message or highlight the fields
                    Toast.makeText(this, "Invalid input. Please enter a valid name and quantity.", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun validateInput(name: String, quantity: Int): Boolean {
        return name.isNotEmpty() && quantity > 0
    }

    private fun updateEmptyStateView() {
        val emptyStateView: TextView = findViewById(R.id.emptyStateView)
        if (shoppingList.isEmpty()) {
            emptyStateView.visibility = View.VISIBLE
        } else {
            emptyStateView.visibility = View.GONE
        }
    }

    private fun addItemToList(name: String, quantity: Int) {
        val newItem = ShoppingItem(name, quantity)
        adapter.addItem(newItem)
    }
}
