package com.example.doma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.editTextEmail
import kotlinx.android.synthetic.main.activity_sign_in.editTextPassword

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        btnGoToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        btnSignIn.setOnClickListener {
            doLogIn()
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(this, ScheduleActivityBasic::class.java))
                finish()
            }
            else{
                Toast.makeText(baseContext, "Please verify email.",
                    Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(baseContext, "Log in failed.",
                Toast.LENGTH_SHORT).show()
        }
    }
    private fun doLogIn(){
        if(editTextEmail.text.toString().isEmpty()){
            editTextEmail.error = "Please enter Email"
            editTextEmail.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
            editTextEmail.error = "Please enter valid Email"
            editTextEmail.requestFocus()
            return
        }

        if(editTextPassword.text.toString().isEmpty()){
            editTextPassword.error = "Please enter Password"
            editTextPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Log in failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        }
    }
