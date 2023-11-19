package com.example.autolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainScreenActivity : AppCompatActivity() {
    private lateinit var listsRecyclerView: RecyclerView
    private lateinit var listsAdapter: ListTitlesAdapter
    private var listTitles = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        listsRecyclerView = findViewById(R.id.listsRecyclerView)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)

        listTitles.add("Groceries")
        listTitles.add("Hardware Store")

        listsAdapter = ListTitlesAdapter(listTitles) { listTitle ->
            // Handle list item click
            val intent = Intent(this, ListActivity::class.java).apply {
                putExtra("LIST_NAME", listTitle)
            }
            startActivity(intent)
        }

        listsRecyclerView.adapter = listsAdapter

        val btnNewList: Button = findViewById(R.id.btn_new_list)
        btnNewList.setOnClickListener {
            promptForNewListName()
        }
    }

    private fun promptForNewListName() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_new_list, null)
        val editTextNewListName = dialogView.findViewById<EditText>(R.id.editTextNewListName)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("New List Name")
            .setPositiveButton("Create") { dialog, _ ->
                val listName = editTextNewListName.text.toString().trim()
                if (listName.isNotEmpty()) {
                    createNewList(listName)
                } else {
                    Toast.makeText(this, "List name cannot be empty.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun createNewList(listName: String) {
        val intent = Intent(this, ListActivity::class.java).apply {
            putExtra("LIST_NAME", listName)
        }
        listTitles.add(listName)
        listsAdapter.notifyItemInserted(listTitles.size - 1)
        startActivity(intent)
    }


}
