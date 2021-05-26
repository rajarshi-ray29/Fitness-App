package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ownerLogin : AppCompatActivity() {
    private var email: String? = null
    private var password: String? = null

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_login)

        etEmail = findViewById<View>(R.id.et_ownerid) as EditText
        etPassword = findViewById<View>(R.id.et_ownerpassword) as EditText
        btnLogin = findViewById<View>(R.id.btn_ownerlogin) as Button
        btnLogin!!.setOnClickListener { loginUser() }



    }


    private fun loginUser() {

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (email.equals("admin") && password.equals("admin")) {
            val intent = Intent(this@ownerLogin, ownerInterface::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
        }
    }
}