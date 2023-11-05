package com.example.roof


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.roof.databinding.ActivityCurrentScoreBinding
import com.example.roof.models.Building
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


class CurrentScore : AppCompatActivity() {
    private lateinit var binding: ActivityCurrentScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentScoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonGPT.setOnClickListener{
            getGPTResponse {

            }
        }

        val building = intent.getSerializableExtra("bld") as Building

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

    private fun getGPTResponse(callback: (String)->Unit) {
        val secretStr = "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"
        val url = "https://api.openai.com/v1/chat/completions";

        val client = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();

        val jsonPayload = """{
  "model": "gpt-3.5-turbo",
  "max_tokens": 1000,
  "messages": [{"role": "user", "content": "You are a virtual assistant that helps users improve their building by giving them advice on how to be more energy efficient and how you could also save them money. Give them 3 options to choose from, and write each of them in the form of a short text. Options should not have more than 20 words and also have pricing for each of them. In addition to those 20 words write how much time they would take to realise. after that, write a new point for each of them saying: this will improve your overall building efficiency score by: num%, where num is: the number of days that the project took (for example if it takes one day that's an additional 1%, if it takes 2 days that's additional 2%, if it takes 3 days that's additional 3%) in addition to 2% for every 50${'$'} that would be spent on the project (for example if ${'$'}50 would be spent then its additional 2%, if ${'$'}100 is spent, that's additional 4% and so on taking the lower cost that you have suggested) and write that as one number of per cent. use the words to describe how to realise the project for example saying what to switch out and what to add, use as least as possible words for the cost, time to realize and efficiency score without additional data for the efficiency score just percentage."}]}"""

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body: RequestBody = JSONObject(jsonPayload).toString().toRequestBody()

        val headers = Headers.Builder()
            .add("Authorization", "Bearer $secretStr")
            .add("Content-Type", "application/json")
            .build()

        val request = Request.Builder().url(url).headers(headers).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Response", "Recieved response")

                if (!response.isSuccessful) {
                    Log.e("Error", "Neka greska")
                    val body = response?.body?.string()
                    Log.e("ErrorResponse", body!!)

                } else {

                    val body = response?.body?.string()
                    //val aqi = json.get("data");
                    Log.d("ResponseBody", body!!)
                    //val jsonObject = JSONObject(body)
                    //val jsonArray = jsonObject.getJSONObject("data")
                    //val text = jsonArray.getString("aqi").toString()
                    //binding.airQualityTextView.setText(text)
                    callback("test")

                }


            }

        })
    }

    fun goMap(view: View) {
        startActivity(Intent(this, MapsActivity::class.java))

    }
}