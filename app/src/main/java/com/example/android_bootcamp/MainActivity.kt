package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.android_bootcamp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners() {
        search()


        binding.addNewUser.setOnClickListener {
            val intent = Intent(this, AddUser::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        research()

    }


    private fun search() {
        binding.searchBar.doOnTextChanged { text, _, _, _ ->
            val enteredText = text?.toString() ?: ""
            val foundUser = User.users.firstOrNull { user ->
                user.searchUser(enteredText)
            }
            if (enteredText == "") {
                binding.resultId.visibility = View.GONE
                binding.addNewUser.visibility = View.GONE
            } else {
                if (foundUser != null) {
                    binding.resultId.text = foundUser.toString()
                    binding.resultId.visibility = View.VISIBLE
                } else {
                    binding.addNewUser.visibility = View.VISIBLE
                    binding.resultId.text = getString(R.string.user_not_found)
                    binding.resultId.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun research() {
        val enteredText = binding.searchBar.text?.toString() ?: ""
        val foundUser = User.users.firstOrNull { user ->
            user.searchUser(enteredText)
        }
        if (enteredText == "") {
            binding.resultId.visibility = View.GONE
            binding.addNewUser.visibility = View.GONE
        } else {
            if (foundUser != null) {
                binding.resultId.text = foundUser.toString()
                binding.resultId.visibility = View.VISIBLE
            } else {
                binding.addNewUser.visibility = View.VISIBLE
                binding.resultId.text = getString(R.string.user_not_found)
                binding.resultId.visibility = View.VISIBLE
            }
        }

    }

}

