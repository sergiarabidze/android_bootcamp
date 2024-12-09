package com.example.android_bootcamp
fun String.countOccurences(char:Char):Int {
    var counter:Int = 0
    for (value in this){
        if(value==char){
            counter++
        }
    }
    return counter
}
fun String.isAnagram(second:String):Boolean{
    if(this.length!=second.length){
        return false
    }
    for (char in this){
        if(this.countOccurences(char)!=second.countOccurences(char)){
            return false
        }
    }
    return true
}