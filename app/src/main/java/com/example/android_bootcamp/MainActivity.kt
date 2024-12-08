package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_bootcamp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding//we are using viewbinding because its easier to use works faster and is null safe
    private var users = mutableMapOf<String,User>()//hashmap for storing users we map email to user objects.
    private var users_counter = 0//user counter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }
    @SuppressLint("SetTextI18n")
    fun setUp(){
        val email = binding.emailId
        val fullName = binding.fullnameId
        val addUser = binding.addId
        val text = binding.textId
        val userCounter = binding.usersCounterId
        val getUserInfoBt = binding.btGetUserInfoId
        val registeredEmail = binding.userEmailId
        val userInfoProvider = binding.userInfoId
        //assigning each activity views to variables
        addUser.setOnClickListener{
            val emailString = email.text.toString()
            val fullNameString = fullName.text.toString()

            if(emailString.isEmpty()||fullNameString.isEmpty()){
              text.setText("Enter all the information.")
                text.visibility = View.VISIBLE
                return@setOnClickListener
            }//if user did not enter email or full name we should tell him to do so
            if(!emailString.isValidEmail()){
                text.setText("Email you provided is not valid.")
                text.visibility = View.VISIBLE
                return@setOnClickListener
            }//checking email validity
            if (emailString in users.keys){
                text.setText("You are already registered.")
                text.visibility = View.VISIBLE
                return@setOnClickListener
            }//we cant register two users with only one email
            users[emailString] = User(email = emailString, fullName = fullNameString)
            userCounter.setText("users: ${++users_counter}")//it will first increment counter value and then we use it
            text.visibility = View.GONE
            email.setText("")
            fullName.setText("")//when user is added we should clear edit text views

        }
        getUserInfoBt.setOnClickListener{
            val registeredEmailString = registeredEmail.text.toString()
            if(registeredEmailString.isEmpty()){
                userInfoProvider.setText("Enter the email.")
                userInfoProvider.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if (registeredEmailString !in users.keys){
                userInfoProvider.setText("User not found")
                userInfoProvider.visibility = View.VISIBLE
                return@setOnClickListener
            }//if the email user provides is already present in the map's keys we should return String representation of user's information
            userInfoProvider.text = users[registeredEmailString].toString()
            userInfoProvider.visibility = View.VISIBLE

        }



    }
}