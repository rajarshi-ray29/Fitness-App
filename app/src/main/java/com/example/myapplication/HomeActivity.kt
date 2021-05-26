package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeActivity: AppCompatActivity() {
    private val TAG="HomeActivity"

    private var email: String?=null
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var mProgressBar: ProgressDialog? = null

    //UI elements
    private var tvName: TextView? = null
    private var btnWorkout:Button?=null
    private var btnDiet:Button?=null
    private var btnStore:Button?=null
    private var btnEdit:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initialise()
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        tvName = findViewById<View>(R.id.tv_username) as TextView
        btnWorkout = findViewById<View>(R.id.btn_workoutplan) as Button
        btnDiet = findViewById<View>(R.id.btn_dietplan) as Button
        btnStore = findViewById<View>(R.id.btn_supplement_store) as Button
        btnEdit = findViewById<View>(R.id.btn_edit_info) as Button
        mProgressBar = ProgressDialog(this)

        btnEdit!!
            .setOnClickListener { startActivity(Intent(this,
                EditInfoActivity::class.java)) }
        btnStore!!.setOnClickListener {
            startActivity(Intent(this, StoreActivity::class.java))
        }
        btnWorkout!!.setOnClickListener {
            startActivity(Intent(this, WorkoutPlanActivity::class.java))
        }
        btnDiet!!.setOnClickListener {
            startActivity(Intent(this, DietPlanActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvName!!.text = snapshot.child("name").value as String
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}