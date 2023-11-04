package com.example.roof

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

class BuldingSpecs : AppCompatActivity() {

    private val SELECT_IMAGE_REQUEST = 100
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulding_specs)


        imageView = findViewById(R.id.imageView)
        val button = findViewById<Button>(R.id.buttonNext)
        button.setOnClickListener {
            val intent = Intent(this, CurrentScore::class.java)
            startActivity(intent)
        }

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, SELECT_IMAGE_REQUEST)
        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
            imageView.setImageBitmap(bitmap)
        }
    }
}