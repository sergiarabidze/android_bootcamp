package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMainLayout()//main function where we set content view for the main layout
    }

    private fun showMainLayout() {
        setContentView(R.layout.activity_main)


        val email: EditText = findViewById(R.id.Email_id)
        val userName: EditText = findViewById(R.id.username_id)
        val firstName: EditText = findViewById(R.id.firstName_id)
        val lastName: EditText = findViewById(R.id.lastName_id)
        val age: EditText = findViewById(R.id.age_id)
        val save: Button = findViewById(R.id.saveButton_id)
        val clear: Button = findViewById(R.id.clearButton_id)//we assign different xml view text and button values to variables

        save.setOnClickListener {
            val emailString: String = email.text.toString()
            val userNameString: String = userName.text.toString()
            val firstNameString: String = firstName.text.toString()
            val lastNameString: String = lastName.text.toString()
            val ageValue: Int? = age.text.toString().toIntOrNull()//here we take values inserted by user and assign it to the variables

            if (emailString.isEmpty() || userNameString.isEmpty() || firstNameString.isEmpty() || lastNameString.isEmpty() || ageValue == null) {
                Toast.makeText(this, "Please enter all the information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener//if anything is wrong we return this clickListener function because we don't want to save incomplete information
            }//if any of the information is not provided by user we should show toast indicating that user must enter all the information

            if (userNameString.length < 10) {
                Toast.makeText(
                    this, "Username should be at least 10 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }//if username is too short we still send toast

            if (!emailString.emailValidation()) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }//we have extension function for checking valid emails

            if (ageValue <= 0) {
                Toast.makeText(this, "Age must be a positive number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }//obviously age is more than 0

            showProfileLayout(
                emailString,
                userNameString,
                firstNameString,
                lastNameString,
                ageValue
            )//then we call another function for different layout and pass arguments for filling second layout with provided information
        }

        clear.setOnClickListener {
            Toast.makeText(this, "Click for a while to clear data", Toast.LENGTH_SHORT).show()
        }//so if user clicks clear button we should tell him/her that clear works when we press for a while

        clear.setOnLongClickListener {
            email.setText("")
            userName.setText("")
            firstName.setText("")
            lastName.setText("")
            age.setText("")
            true
        }//after pressing for a while all the inserted info is cleared
    }
    private fun showProfileLayout(
        emailString: String,
        userNameString: String,
        firstNameString: String,
        lastNameString: String,
        ageValue: Int
    ) {
        setContentView(R.layout.activity_profile_info)//set new layout

        val emailInfo: TextView = findViewById(R.id.emailInfo_id)
        val userNameInfo: TextView = findViewById(R.id.usernameInfo_id)
        val usersNameInfo: TextView = findViewById(R.id.usersNameInfo_id)
        val ageInfo: TextView = findViewById(R.id.ageInfo_id)
        val againButton: Button = findViewById(R.id.againButton_id)//collecting views in the variables

        emailInfo.text = "Email: $emailString"
        userNameInfo.text = "Username: $userNameString"
        usersNameInfo.text = "Full Name: $firstNameString $lastNameString"
        ageInfo.text = "Age: $ageValue"//assigning values to the components

        againButton.setOnClickListener {
            showMainLayout()
        }//if user clicks again button we should call old function for main layout
    }
}
