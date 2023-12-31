package com.example.roof

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roof.databinding.ActivityStartScreenBinding
import com.example.roof.models.AppControllerTest

class StartScreen : AppCompatActivity() {

    private lateinit var binding: ActivityStartScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.goButton.setOnClickListener {
            AppControllerTest.Companion.test(this)
            startActivity(Intent(this, MapsActivity::class.java))
        }

    }
}