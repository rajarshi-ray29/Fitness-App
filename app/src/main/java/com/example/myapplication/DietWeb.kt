package com.example.myapplication

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DietWeb: AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diet_web)

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
                var gender = snapshot.child("gender").value as String
                var age = snapshot.child("age").value as String
                val bmi = weight!!.toFloat()/(height!!.toFloat()* height!!.toFloat()*0.0001)
                var bmr: Double
                if(gender.equals("Male")){
                    bmr = 10*weight.toFloat() + 6.25*height.toFloat() - 5*age.toFloat() +5
                }
                else{
                    bmr = 10*weight.toFloat() + 6.25*height.toFloat() - 5*age.toFloat() -161
                }
                val myWebView: WebView = findViewById(R.id.webview)
                if (bmr<1200) {
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/e_Book-1-1.pdf")
                }
                else if (1200<=bmr&&bmr<1500){
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/Muscular_8_eBook.pdf")
                }
                else if (1500<=bmr&&bmr<1800){
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/LEAN_MODE_Nutrition_Plan_MORNING___EVENING_by_Guru_Mann.pdf")
                }
                else if (bmr>1800){
                    myWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=https://www.gurumannnutrition.com/wp-content/uploads/2018/06/Pure_Mass_Nutrition_Plan_by_Guru_Mann_-1.pdf")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}