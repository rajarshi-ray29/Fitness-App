package com.example.myapplication

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class EditInfoActivity : AppCompatActivity() {
    private val TAG="EditInfoActivity"

    private var email: String?=null
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var mProgressBar: ProgressDialog? = null

    //UI elements
    private var tvName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvEmailVerifiied: TextView? = null
    private var tvHeight: TextView? = null
    private var tvWeight: TextView? = null
    private var tvGender: TextView? = null
    private var tvAge: TextView? = null
    private var btnUpdate: Button?=null
    private var btnSignOut:Button?=null
    private var btnImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        initialise()
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        tvName = findViewById<View>(R.id.tv_first_name) as TextView
        tvEmail = findViewById<View>(R.id.tv_email) as TextView
        tvEmailVerifiied = findViewById<View>(R.id.tv_email_verifiied) as TextView
        tvHeight = findViewById<View>(R.id.tv_height) as TextView
        tvWeight = findViewById<View>(R.id.tv_weight) as TextView
        tvGender = findViewById<View>(R.id.tv_gender) as TextView
        tvAge = findViewById<View>(R.id.tv_age) as TextView
        btnUpdate = findViewById<View>(R.id.btn_update) as Button
        btnSignOut = findViewById<View>(R.id.et_signout) as Button
        btnImage = findViewById<View>(R.id.ib_profile_pic) as ImageView
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
                tvName!!.text = snapshot.child("name").value as String
                tvAge!!.text = snapshot.child("age").value as String
                tvHeight!!.text ="${snapshot.child("height").value as String} cm"
                tvWeight!!.text = "${snapshot.child("weight").value as String} kg"
                tvGender!!.text = snapshot.child("gender").value as String
                if(tvGender!!.text.equals("Male")){
                    btnImage!!.setImageResource(R.drawable.iv_profilepic_male)
                }
                else if(tvGender!!.text.equals("Female")){
                    btnImage!!.setImageResource(R.drawable.iv_profilepic_female)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        btnUpdate!!.setOnClickListener {
            showUpdateDialog(tvName,tvAge,tvHeight,tvWeight)
        }
        btnSignOut!!.setOnClickListener { signoutUser() }
    }

    fun showUpdateDialog(
        tvName : TextView?,
        tvAge: TextView?,
        tvHeight: TextView?,
        tvWeight: TextView?
    ) {
        val builder= AlertDialog.Builder(this)
        val inflater= LayoutInflater.from(this)
        val view=inflater.inflate(R.layout.layout_update,null)
        val etName=view.findViewById<EditText>(R.id.et_name)
        val etAge=view.findViewById<EditText>(R.id.et_age)
        val etHeight=view.findViewById<EditText>(R.id.et_height)
        val etWeight=view.findViewById<EditText>(R.id.et_weight)

        etName.setText(tvName!!.text.toString())
        etAge.setText(tvAge!!.text.toString())
        etHeight.setText(tvHeight!!.text.toString().substring(0,tvHeight!!.text.toString().indexOf(' ')))
        etWeight.setText(tvWeight!!.text.toString().substring(0,tvWeight!!.text.toString().indexOf(' ')))

        builder.setTitle("Edit Info:")
        builder.setView(view)
        builder.setPositiveButton("Update") { dialog, which ->
            val name=etName.text.toString()
            val age=etAge.text.toString()
            val height=etHeight.text.toString()
            val weight=etWeight.text.toString()

            if(name.isEmpty()||age.isEmpty()||height.isEmpty()||weight.isEmpty()){
                Toast.makeText(this, "Please Enter Valid Details",Toast.LENGTH_LONG).show()
                return@setPositiveButton
            }
            //val idk= EditInfoActivity(tvName?.id, name)
            val mUser = mAuth!!.currentUser
            val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
            mUserReference!!.child("name").setValue(name)
            mUserReference!!.child("age").setValue(age)
            mUserReference!!.child("height").setValue(height)
            mUserReference!!.child("weight").setValue(weight)
            Toast.makeText(this, "Updated",Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert= builder.create()
        alert.show()
    }

    private fun signoutUser() {
        mAuth!!.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}



