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
import com.example.roof.databinding.ActivityBuldingSpecsBinding
import com.example.roof.databinding.ActivityCurrentScoreBinding
import com.example.roof.models.Building
import com.example.roof.models.Position
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import com.google.android.gms.maps.model.LatLng
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
    private lateinit var etLocation: EditText

    private var lat: Double = 0.0
    private var lng: Double = 0.0

    private lateinit var binding : ActivityBuldingSpecsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuldingSpecsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getDataFromAPI(){
            runOnUiThread{
                binding.airQualityTextView.setText("Air Quality = "+ it)
            }
        }



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

    private fun getDataFromAPI(callback: (String)->Unit) {
        val url = "https://api.waqi.info/feed/here/?token=d7f28bd96ed37a0c3a27143627a44499b8a1405c";

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient();

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Response", "Recieved response")


                if (!response.isSuccessful) {
                    Log.e("Error", "Neka greska")
                    val body = response?.body?.toString()
                    Log.e("ErrorResponse", body!!)

                } else {

                    val body = response?.body?.string()
                    //val aqi = json.get("data");
                    Log.d("ResponseBody", body!!)
                    val jsonObject = JSONObject(body)
                    val jsonArray = jsonObject.getJSONObject("data")
                    val text = jsonArray.getString("aqi").toString()
                    //binding.airQualityTextView.setText(text)
                    callback(text)

                }


            }

        })
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
            etLocation.text.toString(),
            lat, lng,
            etFloors.text.toString().toInt(),
            etSqm.text.toString().toDouble(),
            etYearBuilt.text.toString().toInt(),
            etInsolationQuality.text.toString().toDouble(),
            cbSolarPanel.isChecked,
            cbParking.isChecked,
            false,
            cbLift.isChecked,
            cbGreenPass.isChecked
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