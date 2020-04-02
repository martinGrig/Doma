package com.example.doma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        btnGoToSignUp.setOnClickListener {
            startActivity(Intent(this, ScheduleActivityBasic::class.java))
            finish()
        }
    }
}
