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

class DietPlanActivity:AppCompatActivity() {
    private val TAG="DietPlanActivity"

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var tvName: TextView? = null
    private var tvWeight: TextView? = null
    private var tvHeight: TextView?=null
    private var tvBmi: TextView?=null
    private var tvBodyType: TextView?=null
    private var tvCalorie: TextView?=null
    private var tvCalorieTarget: TextView?=null
    private var btnDiet: Button?=null
    private var ivDiet: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)
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
        tvCalorie = findViewById<View>(R.id.calorie) as TextView
        tvCalorieTarget = findViewById<View>(R.id.calorietarget) as TextView
        btnDiet = findViewById<View>(R.id.btn_pdf) as Button
        ivDiet = findViewById<View>(R.id.iv_diet) as ImageView
        btnDiet!!.setOnClickListener{
            startActivity(Intent(this, DietWeb::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvName!!.text = "${snapshot.child("name").value as String}'s Diet"
                tvWeight!!.text = "${snapshot.child("weight").value as String} kg"
                tvHeight!!.text = "${snapshot.child("height").value as String} cm"
                ivDiet!!.setImageResource(R.drawable.iv_diet)
                var weight: String = snapshot.child("weight").value as String
                var height: String = snapshot.child("height").value as String
                var gender: String = snapshot.child("gender").value as String
                var age: String = snapshot.child("age").value as String
                val bmi = weight.toFloat()/(height.toFloat()*height.toFloat()*0.0001)
                var bmr: Double
                if(gender.equals("Male")){
                    bmr = 10*weight.toFloat() + 6.25*height.toFloat() - 5*age.toFloat() +5
                    tvCalorie!!.text = "%.2f".format(bmr)
                }
                else{
                    bmr = 10*weight.toFloat() + 6.25*height.toFloat() - 5*age.toFloat() -161
                    tvCalorie!!.text = "%.2f".format(bmr)
                }
                tvBmi!!.text = "%.2f".format(bmi)
                if (bmi<18.5) {
                    tvBodyType!!.text = "Under Weight"
                    tvCalorieTarget!!.text = "${bmr+500}"
                }
                else if (18.5<=bmi&&bmi<22){
                    tvBodyType!!.text = "Athletic"
                    tvCalorieTarget!!.text = "${bmr+300}"
                }
                else if (22<=bmi&&bmi<25){
                    tvBodyType!!.text = "Normal"
                    tvCalorieTarget!!.text = "${bmr}"
                }
                else if (bmi>25){
                    tvBodyType!!.text = "Overweight"
                    tvCalorieTarget!!.text = "${bmr-500}"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}