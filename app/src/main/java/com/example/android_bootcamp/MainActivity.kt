package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val users: MutableMap<String, User> = mutableMapOf()//we put email,user pairs here
    private var deletedUserCounter = 0//we track deleted users' number here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listeners()
    }

    private fun listeners() {
        binding.addUserBtnId.setOnClickListener {
            addUser()
        }

        binding.removeUserBtnId.setOnClickListener {
            removeUser()
        }

        binding.updateUserBtnId.setOnClickListener {
            updateUser()
        }
    }

    //so when we are removing user we just need to enter email and it will be removed
    //I am doing it like that because in my code there is no user with the same email


    @SuppressLint("SetTextI18n")
    private fun removeUser() {
        val email = binding.emailId.text.toString()
        if (email.isNotEmpty()) {
            if (email in users.keys) {
                users.remove(email)
                binding.deletedUsersCounterId.text = "deleted users: ${++deletedUserCounter}"//we update counter for deleted and remaining users
                binding.addedUsersCounterId.text = "active users: ${users.size}"
                binding.successErrorId.text = getString(R.string.success)
                binding.successErrorId.setTextColor(getColor(R.color.green))
                Toast.makeText(this, "User removed", Toast.LENGTH_SHORT).show()
                binding.emailId.text?.clear()
            } else {
                binding.successErrorId.text = getString(R.string.error)
                binding.successErrorId.setTextColor(getColor(R.color.red))
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }
//when adding user we add email and user and it must be unique email
    @SuppressLint("SetTextI18n")
    private fun addUser() {
        val firstName = binding.firstNameId.text.toString()
        val lastName = binding.lastNameId.text.toString()
        val age = binding.ageId.text.toString()
        val email = binding.emailId.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && email.isNotEmpty()) {//if any of the fields is empty we don't add user
            if (!email.isValidEmail()) {
                binding.successErrorId.text = getString(R.string.error)
                binding.successErrorId.setTextColor(getColor(R.color.red))
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                return
            }
            if (age.toInt() < 0) {//if age is negative we don't add user
                binding.successErrorId.text = getString(R.string.error)
                binding.successErrorId.setTextColor(getColor(R.color.red))
                Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show()
                return
            }
            if (!users.containsKey(email)) {//if email is unique we add user
                val user = User(firstName, lastName, age.toInt(), email)
                users[user.email] = user
                binding.addedUsersCounterId.text = "active users: ${users.size}"//we update counter
                binding.successErrorId.text = getString(R.string.success)
                binding.successErrorId.setTextColor(getColor(R.color.green))
                //we always put warning if it went successfully or not
                Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show()
                binding.firstNameId.text?.clear()
                binding.lastNameId.text?.clear()
                binding.ageId.text?.clear()
                binding.emailId.text?.clear()
                //after adding user we clear edittext views
            } else {
                binding.successErrorId.text = getString(R.string.error)
                binding.successErrorId.setTextColor(getColor(R.color.red))
                Toast.makeText(this, "User already exists with the same email", Toast.LENGTH_SHORT)
                    .show()
                return
            }//if email is not unique we don't add user
        }else{
            binding.successErrorId.text = getString(R.string.error)
            binding.successErrorId.setTextColor(getColor(R.color.red))
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }

    }


    private fun updateUser() {
        val firstName = binding.firstNameId.text.toString()
        val lastName = binding.lastNameId.text.toString()
        val age = binding.ageId.text.toString()
        val email = binding.emailId.text.toString()
        //still the same logic we change user according to its email
        if (email.isEmpty()) {
            binding.successErrorId.text = getString(R.string.error)
            binding.successErrorId.setTextColor(getColor(R.color.red))
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
        } else {
            if (email in users.keys) {
                users.plus(
                    email to
                            User(
                                firstName.ifEmpty { users[email]?.firstName.toString() },
                                lastName.ifEmpty { users[email]?.lastName.toString() },
                                if (age.isNotEmpty()) age.toInt() else users[email]?.age ?: 0,
                                email.ifEmpty { users[email]?.email.toString() })
                )
                //here we user can leave editTexts empty and it will not change user's data it will stay the same as it used to be before updating

                binding.successErrorId.text = getString(R.string.success)
                binding.successErrorId.setTextColor(getColor(R.color.green))
                Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
                binding.firstNameId.text?.clear()
                binding.lastNameId.text?.clear()
                binding.ageId.text?.clear()
                binding.emailId.text?.clear()
            } else {
                binding.successErrorId.text = getString(R.string.error)
                binding.successErrorId.setTextColor(getColor(R.color.red))
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }
}