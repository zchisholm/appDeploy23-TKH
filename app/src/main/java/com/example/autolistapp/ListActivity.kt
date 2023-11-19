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
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar

class ListActivity : AppCompatActivity() {
    private lateinit var adapter: ShoppingListAdapter
    private val shoppingList = mutableListOf(ShoppingItem("Apples", 5), ShoppingItem("Bread", 1))

    private lateinit var listName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listName = intent.getStringExtra("LIST_NAME") ?: "New List"
        title = listName

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ShoppingListAdapter(shoppingList,
            onItemSingleTap = { item ->
                // Toggle crossed-out state of the item
                item.isCrossedOut = !item.isCrossedOut
                adapter.notifyItemChanged(shoppingList.indexOf(item))
            },
            onItemDoubleTap = { item ->
                val position = shoppingList.indexOf(item)
                // Open a dialog to edit the item
                showEditItemDialog(item, position)
            }
        )

        val fab: FloatingActionButton = findViewById(R.id.fab_add_item)
        fab.setOnClickListener {
            showAddItemDialog()
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false // not needed for swipe functionality
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedItem = shoppingList[position] // Save the item to restore it if the user undoes the action

                // Remove the item from the list and adapter
                shoppingList.removeAt(position)
                adapter.notifyItemRemoved(position)

                // Show the Snackbar with an Undo option
                Snackbar.make(recyclerView, "${deletedItem.name} removed", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO") {
                        // Undo the delete operation
                        shoppingList.add(position, deletedItem)
                        adapter.notifyItemInserted(position)
                    }
                    show()
                }

                // Update empty state view
                updateEmptyStateView()
            }
        }

        // Attach ItemTouchHelper to RecyclerView
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    // Implement the onItemClick method
    fun onItemClick(item: ShoppingItem) {
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
                val itemQuantityString = editTextItemQuantity.text.toString().trim()

                val itemQuantity = if (itemQuantityString.isEmpty()) null else itemQuantityString.toIntOrNull()

                if (validateInput(itemName, itemQuantity)) {
                    addItemToList(itemName, itemQuantity)
                    updateEmptyStateView()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Invalid input. Please enter a valid name.", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditItemDialog(item: ShoppingItem, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val editTextItemName = dialogView.findViewById<EditText>(R.id.editTextItemName)
        val editTextItemQuantity = dialogView.findViewById<EditText>(R.id.editTextItemQuantity)

        // Pre-populate dialog with current item details
        editTextItemName.setText(item.name)
        editTextItemQuantity.setText(item.quantity?.toString() ?: "")

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Item")
            .setPositiveButton("Update") { dialog, _ ->
                val updatedName = editTextItemName.text.toString().trim()
                val updatedQuantityString = editTextItemQuantity.text.toString().trim()
                val updatedQuantity = if (updatedQuantityString.isEmpty()) null else updatedQuantityString.toIntOrNull()

                if (validateInput(updatedName, updatedQuantity)) {
                    // Update the item in the shopping list
                    item.name = updatedName
                    item.quantity = updatedQuantity
                    adapter.notifyItemChanged(position)
                    updateEmptyStateView()
                } else {
                    Toast.makeText(this, "Invalid input. Please enter a valid name and quantity.", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun validateInput(name: String, quantity: Int?): Boolean {
        return name.isNotEmpty() && (quantity == null || quantity > 0)
    }

    private fun updateEmptyStateView() {
        val emptyStateView: TextView = findViewById(R.id.emptyStateView)
        emptyStateView.visibility = if (shoppingList.isEmpty()) View.VISIBLE else View.GONE
    }


    private fun addItemToList(name: String, quantity: Int?) {
        val newItem = ShoppingItem(name, quantity ?: 0)
        shoppingList.add(newItem)
        //Log.d("ListActivity", "Item added: ${newItem.name}")
        adapter.notifyItemInserted(shoppingList.size - 1)
        updateEmptyStateView()
    }



}
