package com.example.android_bootcamp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityRegisterFirstBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterActivityFirst : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
        }

    private fun listeners(){
        binding.backBtnTwoId.setOnClickListener{
            finish()
        }//it kills the activity and goes back to the previous one
        binding.nextBtnId.setOnClickListener{
            next()
        }
    }
    fun next(){

        val email = binding.emailId.text.toString()//email string
        val password = binding.passwordId.text.toString()//password string
        if (email.isNotEmpty() && password.isNotEmpty()) {//we dont need to check if the password is long enough firebase does it for us
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,  RegisterActivitySecond::class.java)
                        startActivity(intent)//it goes to the next step of registration

                    } else {
                        Toast.makeText(this, "you are already registered", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        }//if the fields are empty we show a toast


    }
    }