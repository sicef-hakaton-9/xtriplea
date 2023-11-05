package com.example.roof

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Checkable
import android.widget.EditText
import android.widget.ImageView
import com.example.roof.models.Building
import com.example.roof.models.Position
import java.io.Serializable

class BuldingSpecs : AppCompatActivity() {

    private val SELECT_IMAGE_REQUEST = 100
    private lateinit var imageView: ImageView
    private lateinit var cbGreenPass: CheckBox
    private lateinit var cbParking: CheckBox
    private lateinit var etInsolationQuality: EditText
    private lateinit var cbSolarPanel: CheckBox
    private lateinit var etYearBuilt: EditText
    private lateinit var cbLift: CheckBox
    private lateinit var etFloors: EditText
    private lateinit var etSqm: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulding_specs)


        imageView = findViewById(R.id.imageView)
        cbSolarPanel = findViewById(R.id.cbSolarPanel)
        cbGreenPass = findViewById(R.id.cbGreenPass)
        cbParking = findViewById(R.id.cbParking)
        etInsolationQuality = findViewById(R.id.etInsolationQuality)
        etYearBuilt = findViewById(R.id.etYearBuilt)
        cbLift = findViewById(R.id.cbLift)
        etFloors = findViewById(R.id.etFloors)
        etSqm = findViewById(R.id.etSqm)

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

    fun nextBtnClicked(view: View) {

        val building : Building = Building(
            "Nepoznato bb", //TODO ADRESSS
            0.0, 0.0, //TODO POSTIONI
            etFloors.text.toString().toInt(),
            etSqm.text.toString().toDouble(),
            etYearBuilt.text.toString().toInt(),
            etInsolationQuality.text.toString().toDouble(),
            cbSolarPanel.isChecked,
            cbParking.isChecked,
            false,
            cbLift.isChecked
            )

        val instance = Singleton
        instance.app.load(this)
        instance.app.addBuilding(building)
        instance.app.save(this)

        val intent = Intent(this@BuldingSpecs, CurrentScore::class.java)
        intent.putExtra("bld", building)
        startActivity(intent)
    }
}