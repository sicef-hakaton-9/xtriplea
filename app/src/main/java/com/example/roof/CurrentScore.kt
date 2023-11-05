package com.example.roof

import android.os.Bundle
import android.util.Log
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
    private var AIR_QUALITY_API = "8fb4861a-b1fc-4bab-98a5-3f4752c88e62"
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

    }
}