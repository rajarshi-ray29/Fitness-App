package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot as DataSnapshot

open class StoreActivity : AppCompatActivity(), ItemAdapter.OnItemClickListener {
    var total: TextView? = null
    var totalcost: Float = 0.00F
    var clear: Button? = null
    var checkout: Button? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        //total = findViewById(R.id.total)
        initialize()
        //updateTotal()
        val myDataset= Datasource().loaddata()
        val recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter= ItemAdapter(this,myDataset,this)
        recyclerView.setHasFixedSize(true)
    }

    private fun initialize() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        val email: String =mUser.email
        var name: String? = null
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name = snapshot.child("name").value as String
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        total = findViewById(R.id.total)
        clear = findViewById(R.id.btn_clear)
        clear!!.setOnClickListener{
            totalcost = 0.00F
            total!!.text = "$totalcost"
        }
        checkout = findViewById(R.id.btn_checkout)
        checkout!!.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL,"rajarshi5675@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Email regarding payment")
            intent.putExtra(Intent.EXTRA_TEXT, "$name has to pay $totalcost amount")
            intent.setType("message/rfc822")
            startActivity(Intent.createChooser(intent, "Select email"));
        }
    }

    override fun onItemCLick(position: Int, cost: Float) {
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show()
        totalcost = totalcost + cost
        total!!.text = "${totalcost}"
    }
}
