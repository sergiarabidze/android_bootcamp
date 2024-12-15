package com.example.android_bootcamp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityRegisterSecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivitySecond : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners() {
        binding.previousBtnId.setOnClickListener {
            finish()
        }//we kill the activity and go back to the previous one

        binding.signUpBtnId.setOnClickListener {
           signUp()
        }

    }
    private fun signUp(){

        val username = binding.userNameId.text.toString().trim()
        val user = FirebaseAuth.getInstance().currentUser//when we registered we are now currentUser so we can assign username to our user

        if (username.isNotEmpty() && user != null) {
            val database = FirebaseDatabase.getInstance().getReference("Users")//it will get or create a table called users in the realtime database
            val userId = user.uid//every registered user have unique userid
            Log.d("DEBUG", "UserID: $userId")

            val userData = mapOf("username" to username, "email" to user.email)
            // we create new node in database where we store username and email for every user
            database.child(userId).setValue(userData)//here datebase is out newly created users table and we get or create this userdata for every user
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Username saved successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        //when we finish registration we go back to main activity
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter a valid username.", Toast.LENGTH_SHORT).show()
        }

    }
}
