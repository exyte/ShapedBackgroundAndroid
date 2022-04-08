package com.exyte.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.likeinstabackgroundtext.R
import com.example.likeinstabackgroundtext.databinding.ActivityMainBinding
import com.exyte.shapedbackground.roundedBackground

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.roundedBackground {
            backgroundColor = resources.getColor(R.color.serenade)
            shadow {
                dx = 5f
                dy = 5f
                radius = 10f
            }
        }
    }
}