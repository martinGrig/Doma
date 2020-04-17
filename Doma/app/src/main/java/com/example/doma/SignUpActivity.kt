package com.example.doma

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mUsername : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signUpUser()
        }



    }

    private fun signUpUser(){
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

        mUsername = findViewById(R.id.editTextUsername) as EditText

        auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val auth = FirebaseAuth.getInstance()
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, SignInActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
                fun onComplete(@NonNull task: Task<AuthResult?>) {
                    if (!task.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, "sign up error", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val user_id: String = auth.getCurrentUser()!!.getUid()
                        val user = User(mUsername.text.toString(), editTextEmail.text.toString(), user_id)
                            FirebaseDatabase.getInstance().reference.child("users")
                                .child(user_id).setValue(user)
                        /*val name: String = mUsername.getText().toString()

                        val newPost: MutableMap<*, *> =
                            HashMap<Any?, Any?>()
                        newPost["name"] = name
                        current_user_db.setValue(newPost)*/
                    }
                }

            }

    }
}
