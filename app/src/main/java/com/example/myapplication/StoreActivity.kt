package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

open class StoreActivity : AppCompatActivity() {
    var total: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        //total = findViewById(R.id.total)
        total = findViewById(R.id.total)
        //updateTotal()
        val myDataset= Datasource().loadAffirmation()
        val recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter= ItemAdapter(this,myDataset)
        recyclerView.setHasFixedSize(true)
    }
}
