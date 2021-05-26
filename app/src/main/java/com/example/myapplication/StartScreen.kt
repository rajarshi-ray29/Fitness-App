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

class StartScreen: AppCompatActivity() {
    private val TAG="StartScreen"
    private var btnLogIn: Button? = null
    private var btnCreateAccount: Button? = null
    private var btnOwner: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        btnLogIn=findViewById<View>(R.id.btn_login) as Button
        btnCreateAccount=findViewById<View>(R.id.btn_createacc) as Button
        btnOwner=findViewById<View>(R.id.btn_owneractivity) as Button

    }

    override fun onStart() {
        super.onStart()
        btnLogIn!!.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnCreateAccount!!.setOnClickListener(){
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
        btnOwner!!.setOnClickListener(){
            val intent = Intent(this, ownerLogin::class.java)
            startActivity(intent)
        }
    }
}