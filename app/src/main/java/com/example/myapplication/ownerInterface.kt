package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ownerInterface : AppCompatActivity() {



    private var btn_allcustomers: Button?=null
    private var btn_addcustomer: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_interface)


        btn_allcustomers = findViewById<View>(R.id.btn_Allcustomers) as Button
        btn_addcustomer = findViewById<View>(R.id.btn_add_customer) as Button

        btn_allcustomers!!.setOnClickListener { startActivity(Intent(this,
                OwnerActivity::class.java)
            ) }

        btn_addcustomer!!.setOnClickListener { startActivity(Intent(this,
            CreateAccountActivity::class.java)
        ) }




    }
}