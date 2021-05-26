package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class WorkoutPlanActivity:AppCompatActivity() {
    private val TAG="WorkoutPlanActivity"

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var tvName: TextView? = null
    private var tvWeight: TextView? = null
    private var tvHeight: TextView?=null
    private var tvBmi: TextView?=null
    private var tvBodyType: TextView?=null
    private var btnWorkout: Button?=null
    private var ivWorkout: ImageView? = null
    val bmi: Float? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_plan)
        initialise()
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        tvName = findViewById<View>(R.id.tv_username) as TextView
        tvWeight = findViewById<View>(R.id.tv_weight) as TextView
        tvHeight = findViewById<View>(R.id.tv_height) as TextView
        tvBmi = findViewById<View>(R.id.tv_bmi) as TextView
        tvBodyType = findViewById<View>(R.id.body_type) as TextView
        btnWorkout = findViewById<View>(R.id.btn_pdf) as Button
        ivWorkout = findViewById<View>(R.id.iv_body) as ImageView
        btnWorkout!!.setOnClickListener{
            startActivity(Intent(this, WorkoutWeb::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvName!!.text = "${snapshot.child("name").value as String}'s body"
                tvWeight!!.text = "${snapshot.child("weight").value as String} kg"
                tvHeight!!.text = "${snapshot.child("height").value as String} cm"
                var weight: String = snapshot.child("weight").value as String
                var height: String = snapshot.child("height").value as String
                val bmi = weight.toFloat()/(height.toFloat()*height.toFloat()*0.0001)
                tvBmi!!.text = "%.2f".format(bmi)
                if (bmi<18.5) {
                    tvBodyType!!.text = "Under Weight"
                    ivWorkout!!.setImageResource(R.drawable.iv_underweight)
                }
                else if (18.5<=bmi&&bmi<22){
                    tvBodyType!!.text = "Athletic"
                    ivWorkout!!.setImageResource(R.drawable.iv_athletic)
                }
                else if (22<=bmi&&bmi<25){
                    tvBodyType!!.text = "Normal"
                    ivWorkout!!.setImageResource(R.drawable.iv_normal)
                }
                else if (bmi>25){
                    tvBodyType!!.text = "Overweight"
                    ivWorkout!!.setImageResource(R.drawable.iv_overweight)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}