package com.example.likeinstabackgroundtext

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.likeinstabackgroundtext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.roundedBackground {
            backgroundColor = Color.parseColor("#fff1e4")
            shadow {
                dx = 5f
                dy = 5f
                radius = 10f
            }
        }
    }
}