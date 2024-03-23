package com.example.calmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var memberStatusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nameTextView = findViewById(R.id.tv_name)
        ageTextView = findViewById(R.id.tv_age)
        weightTextView = findViewById(R.id.tv_weight)
        heightTextView = findViewById(R.id.tv_height)
        memberStatusTextView = findViewById(R.id.tv_status)

        val db = FirebaseFirestore.getInstance()

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let {
            db.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null) {

                        val name = "Name: " + document.getString("name")
                        val age = "Age: " +  document.getLong("age")
                        val weight = "Weight: " + document.getDouble("weight")
                        val height = "Height: " + document.getLong("height")
                        val memberStatus = "Membership status: " + document.getBoolean("memberStatus")

                        nameTextView.text = name
                        ageTextView.text = age
                        weightTextView.text = weight
                        heightTextView.text = height
                        memberStatusTextView.text = memberStatus
                    } else {
                        println("No such user exists!")
                    }
                }
                .addOnFailureListener {exception ->

                }
        }
    }
}