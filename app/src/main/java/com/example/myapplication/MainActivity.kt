package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainActivity: AppCompatActivity() {
    private val TAG="MainActivity"

    private var email: String?=null
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var mProgressBar: ProgressDialog? = null

    //UI elements
    private var tvFirstName: TextView? = null
    private var tvLastName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvEmailVerifiied: TextView? = null
    private var tvHeight: TextView? = null
    private var tvWeight: TextView? = null
    private var tvGender: TextView? = null
    private var btnSignOut:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        tvFirstName = findViewById<View>(R.id.tv_first_name) as TextView
        tvLastName = findViewById<View>(R.id.tv_last_name) as TextView
        tvEmail = findViewById<View>(R.id.tv_email) as TextView
        tvEmailVerifiied = findViewById<View>(R.id.tv_email_verifiied) as TextView
        tvHeight = findViewById<View>(R.id.tv_height) as TextView
        tvWeight = findViewById<View>(R.id.tv_weight) as TextView
        tvGender = findViewById<View>(R.id.tv_gender) as TextView
        btnSignOut = findViewById<View>(R.id.et_signout) as Button
        mProgressBar = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        tvEmail!!.text = mUser.email
        tvEmailVerifiied!!.text = mUser.isEmailVerified.toString()

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvFirstName!!.text = snapshot.child("firstName").value as String
                tvLastName!!.text = snapshot.child("lastName").value as String
                tvHeight!!.text = snapshot.child("height").value as String
                tvWeight!!.text = snapshot.child("weight").value as String
                tvGender!!.text = snapshot.child("gender").value as String
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        btnSignOut!!.setOnClickListener { signoutUser() }
    }

    private fun signoutUser() {
        mAuth!!.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}



