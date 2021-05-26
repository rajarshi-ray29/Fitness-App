package com.example.myapplication

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WorkoutWeb: AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workout_web)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.settings.setSupportZoom(true)
        myWebView.settings.javaScriptEnabled = true
    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var weight = snapshot.child("weight").value as String
                var height = snapshot.child("height").value as String
                val bmi = weight!!.toFloat()/(height!!.toFloat()* height!!.toFloat()*0.0001)
                val myWebView: WebView = findViewById(R.id.webview)
                if (bmi<18.5) {
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/e_Book-1-1.pdf")
                }
                else if (18.5<=bmi&&bmi<22){
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/e_Book-1-1.pdf")
                }
                else if (22<=bmi&&bmi<25){
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/e_Book-1-1.pdf")
                }
                else if (bmi>25){
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/e_Book-1-1.pdf")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}