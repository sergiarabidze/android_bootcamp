package com.example.android_bootcamp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityAddUserBinding

class AddUser : AppCompatActivity() {
    private  lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()

    }
    private fun listeners(){
        binding.addNewUserId.setOnClickListener {
            addUser()
        }
    }



    private fun addUser(){
        val id = binding.userId.text.toString()
        val firstName = binding.firstNameId.text.toString()
        val lastName = binding.lastNameId.text.toString()
        val birthday = binding.birthdayId.text.toString()
        val address = binding.addressId.text.toString()

        if (id.toIntOrNull() == null || birthday.toIntOrNull() == null){
            Toast.makeText(this, "ID must be a number", Toast.LENGTH_SHORT).show()
            return
        }

        if(id.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() && address.isNotEmpty()){
            User.users.add(User(id = id.toInt(), firstName = firstName, lastName = lastName, birthday = birthday, address = address, email = "",desc = null))
            Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()
            finish()

        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }

    }
}