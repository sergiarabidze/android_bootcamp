package com.example.android_bootcamp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_bootcamp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var anagrams:MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()


    }
    fun setup(){
        val editText = binding.enterAnagramId
        val saveButton = binding.saveId
        val outputButton = binding.outputId
        val anagramButton = binding.anagramId
        val clearButton = binding.clearId

        saveButton.setOnClickListener {
            val anagramString = editText.text.toString()
            if(anagramString.isEmpty()){
                Toast.makeText(this,"enter word", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            anagrams.add(anagramString)
            editText.setText("")
        }

        outputButton.setOnClickListener {
            val map: MutableMap<String, MutableList<String>> = mutableMapOf()
            for (anagram in anagrams) {
                var isAdded = false
                for (strings in map.keys) {
                    if (anagram.isAnagram(strings)) {
                        map[strings]!!.add(anagram)
                        isAdded = true
                        break
                    }
                }

                if (!isAdded) {
                    map[anagram] = mutableListOf(anagram)
                }

            }

            var output:String = map.size.toString()
            anagramButton.text = "anagrams:${output}"
        }

        clearButton.setOnClickListener {
            anagrams = mutableListOf()
            anagramButton.setText("anagrams:")
        }
    }
 }