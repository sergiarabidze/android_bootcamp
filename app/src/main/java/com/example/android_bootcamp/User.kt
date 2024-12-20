package com.example.android_bootcamp

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class User(var id: Int, var firstName: String, var lastName: String, var birthday: String, var address: String, var email: String, var desc: String?=""){




    fun searchUser(input: String): Boolean {
        val words = input.split(" ").map { it.trim() }

        return words.any { word ->
            word == id.toString() ||
                    word.equals(firstName, ignoreCase = true) ||
                    word.equals(lastName, ignoreCase = true) ||
                    word.equals(address, ignoreCase = true) ||
                    word.equals(email, ignoreCase = true) ||
                    word == formatTimestamp(birthday.toLong())
        }
    }




    override fun toString(): String {
        if (desc != null) {
            return "ID: ${id},\n Name: ${firstName} ${lastName},\n Birthday: ${
                formatTimestamp(birthday.toLong())
            }"
        }else{
            return "ID: ${id},\n Name: ${firstName} ${lastName},\n Birthday: ${
                formatTimestamp(birthday.toLong())
            },\n desc = $desc"
        }
    }

    private fun formatTimestamp(timeStamp: Long): String {
        return if (timeStamp > 0) {
            val date = Date(timeStamp)
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            formatter.format(date)
        } else {
            "Invalid Date"
        }
    }







    companion object{
        var users = mutableListOf<User>(User(
             id = 1,
            firstName = "გრიშა",
         lastName = "ონიანი",
         birthday = "1724647601641",
         address = "სტალინის სახლმუზეუმი",
         email = "grisha@mail.ru"
         ),
         User(
         id = 2,
         firstName = "Jemal",
         lastName = "Kakauridze",
         birthday = "1714647601641", address = "თბილისი, ლილოს მიტოვებული ქარხანა",
         email = "jemal@gmail.com"
         ),User(
                id = 2,
                firstName = "Omger",
                 lastName = "Kakauridze",
             birthday = "1724647701641",
         address = "თბილისი, ასათიანი 18",
         email = "omger@gmail.com"
         ),
         User(
         id =32,
         firstName = "ბორის",
         lastName = "გარუჩავა",
         birthday = "1714947701641",
         address = "თბილისი, იაშვილი 14",
         email = ""
         ),
         User(
         id =34,
         firstName = "აბთო",
         lastName = "სიხარულიძე",
         birthday = "1711947701641",
         address = "ფოთი",
         email = "tebzi@gmail.com",
         desc =null
         )
         )
    }
}


