package com.example.roof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roof.databinding.ActivityCurrentScoreBinding
import com.example.roof.databinding.ActivityGptactivityBinding


private lateinit var binding: ActivityGptactivityBinding
class GPTActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGptactivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val data = intent.getStringExtra("data")
        binding.option1.setText(data)

    }
}