package com.example.roof

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.roof.databinding.ActivityCurrentScoreBinding
import com.example.roof.databinding.ActivityMainBinding
import com.example.roof.models.Building


private lateinit var binding: ActivityCurrentScoreBinding

//private lateinit var tvTrafficFlow: TextView
//private lateinit var tvNoise: TextView
//private lateinit var tvAirQuality: TextView
//private lateinit var tvTotalScore: TextView
//private lateinit var cbGreenPass: CheckBox
//private lateinit var cbPanels: CheckBox
//private lateinit var cbParking: CheckBox
//private lateinit var cbLift: CheckBox
//private lateinit var tvFloors: TextView
//private lateinit var tvSqm: TextView
//private lateinit var tvYear: TextView

class CurrentScore() : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentScoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
}