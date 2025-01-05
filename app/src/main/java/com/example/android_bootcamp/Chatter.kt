package com.example.android_bootcamp

import java.util.UUID


data class Chatter(val id:String = UUID.randomUUID().toString(),val text:String,val time:String,val type:UserType)
//unique id because we use listAdapter and it needs unique ids


enum class UserType{
    NORMAL,
    ADMIN
}//enum for different types
