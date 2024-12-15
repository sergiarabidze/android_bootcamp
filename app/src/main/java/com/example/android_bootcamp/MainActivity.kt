package com.example.android_bootcamp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners() {
        binding.logInId.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }//it goes to log in activity
        binding.registerId.setOnClickListener {
            val intent = Intent(this, RegisterActivityFirst::class.java)
            startActivity(intent)
        }//it goes to register activity
    }
}