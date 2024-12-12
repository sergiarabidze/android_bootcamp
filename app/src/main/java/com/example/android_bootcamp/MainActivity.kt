package com.example.android_bootcamp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_bootcamp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.placeTextId.text = getString(R.string.place)
        binding.priceStringId.text = getString(R.string.price_string)
        binding.continentId.text = getString(R.string.continent)
        binding.priceInNumberId.text = getString(R.string.price)
        binding.currencyId.text = getString(R.string.currency)
        binding.overviewId.text = getString(R.string.overview)
        binding.detailsId.text = getString(R.string.details)
        binding.clockId.text = getString(R.string.time)
        binding.cloudId.text = getString(R.string.temperature)
        binding.scrollViewId.text = getString(R.string.text)
        binding.sendId.text = getString(R.string.book)
        binding.starId.text = getString(R.string.star)


    }
}