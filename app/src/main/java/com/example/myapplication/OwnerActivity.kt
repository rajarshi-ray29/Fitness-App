package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*

class OwnerActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var database: DatabaseReference? = null
    var myAdapter: MyAdapter? = null
    var list: ArrayList<User?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)
        recyclerView = findViewById(R.id.recycler1)
        database = FirebaseDatabase.getInstance().getReference("Users")

        list = ArrayList()
        myAdapter = MyAdapter(this, list)
        database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.child("name") as String        //getValue(User::class.java)
                    list!!.add(user)
                }
                myAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}