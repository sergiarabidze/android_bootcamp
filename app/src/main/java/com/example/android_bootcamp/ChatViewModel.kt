package com.example.android_bootcamp

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Types

class ChatViewModel : ViewModel() {

    private val jsonString = """[
        {
            "id":1,
            "image":"https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
            "owner":"გრიშა ონიანი",
            "last_message":"თავის ტერიტორიას ბომბავდა",
            "last_active":"4:20 PM",
            "unread_messages":3,
            "is_typing":false,
            "last_message_type":"text"
        },
        {
            "id":2,
            "image":null,
            "owner":"ჯემალ კაკაურიძე",
            "last_message":"შემოგევლე",
            "last_active":"3:00 AM",
            "unread_messages":0,
            "is_typing":true,
            "last_message_type":"voice"
        },
        {
            "id":3,
            "image":"https://i.ytimg.com/vi/KYY0TBqTfQg/hqdefault.jpg",
            "owner":"გურამ ჯინორია",
            "last_message":"ცოცხალი ვარ მა რა ვარ შე.. როდის იყო კვტარი ტელეფონზე ლაპარაკობდა",
            "last_active":"1:00 ",
            "unread_messages":0,
            "is_typing":false,
            "last_message_type":"file"
        },
        {
            "id":4,
            "image":"",
            "owner":"კაკო წენგუაშვილი",
            "last_message":"ადამიანი რო მოსაკლავად გაგიმეტებს თანაც ქალი ის დასანდობი არ არი",
            "last_active":"1:00 PM",
            "unread_messages":0,
            "is_typing":false,
            "last_message_type":"text"
        }
    ]"""


    private val chatList: List<Chat>

    init {
        chatList = parseJson()
    }

    private fun parseJson(): List<Chat> {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val listType = Types.newParameterizedType(List::class.java, Chat::class.java)
        val adapter = moshi.adapter<List<Chat>>(listType)

        return try {
            adapter.fromJson(jsonString) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getChats(): List<Chat> {
        return chatList
    }

}
