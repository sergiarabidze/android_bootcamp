package com.example.android_bootcamp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }
    private fun listeners() {
        binding.backBtnId.setOnClickListener {
            finish()
        }

        binding.logInBtnId.setOnClickListener {logIn()}
    }
        private fun logIn(){

            val email = binding.emailLoginId.text.toString()
            val password = binding.passwordLoginId.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser//current user is our registered user
                            val userId = user?.uid//we get our userid

                            if (userId != null) {
                                val database = FirebaseDatabase.getInstance().getReference("Users")//we get our users table

                                database.child(userId).get()//we get the info for the current user in the realtime database
                                    .addOnSuccessListener { snapshot ->
                                        val username = snapshot.value.toString().substringAfter("username=").substringBefore("}")//here we get the value
                                        Toast.makeText(this, "Hello, $username!", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "You are not registered.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter valid email and password.", Toast.LENGTH_SHORT).show()
            }

        }
}
