package com.example.roof
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView

import android.widget.Toast
import com.example.roof.models.Building


import androidx.appcompat.app.AppCompatActivity
import com.example.roof.databinding.ActivityCurrentScoreBinding
import com.google.android.gms.maps.model.LatLng
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class CurrentScore : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentScoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonGPT.setOnClickListener{

            //TODO: Uzeti pravu lokaciju a ne neku izmisljenu
            var loc = LatLng(30.0,30.0)
            val url = "https://api.waqi.info/feed/here/?token=d7f28bd96ed37a0c3a27143627a44499b8a1405c";

            val request = Request.Builder().url(url).build()
            val client = OkHttpClient();

           client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("Response","Recieved response")


                    if(!response.isSuccessful){
                        Log.e("Error","Neka greska")
                        val body = response?.body?.toString()
                        Log.e("ErrorResponse", body!!)

                    }
                    else {

                        val body = response?.body?.string()
                        //val aqi = json.get("data");
                        Log.d("ResponseBody", body!!)


                    }



                }

            })



        }

        val building = intent.getSerializableExtra("bld") as Building

        //for (b in instance.app.buildings){
        //    Log.d("AndrijaKaze", b.address)
        //}

        //Log.d("String se pasuje", strng!!)
        //if(strng != null){
            Toast.makeText(this, building.yearBuiltIn.toString(), Toast.LENGTH_LONG).show()
        //}

        binding.tvYear.text = building.yearBuiltIn.toString()
        binding.tvSqm.text = building.surfaceArea.toString()
        binding.tvFloors.text = building.nFloors.toString()
        binding.cbLift.isChecked = building.lift
        binding.cbParking.isChecked = building.parking
        binding.cbPanels.isChecked = building.solarPanels
        binding.cbGreenPass.isChecked = building.greenPass
        binding.tvTotalScore.text = building.score()
        binding.tvAirQuality.text = building.airQuality().toString()
        binding.tvNoise.text = building.noiseLevel().toString()
        binding.tvTrafficFlow.text = building.trafficFlow().toString()


    }

    fun goMap(view: View) {
        startActivity(Intent(this, MapsActivity::class.java))

    }
}