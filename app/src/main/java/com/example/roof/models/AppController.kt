package com.example.roof.models
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

data class AppInfo(val nBuildings: Int)

class AppController {
    public var buildings : Array<Building> = emptyArray()
    public var nBuildings : Int = 0
    private val gson = Gson()

    public fun addBuilding(building: Building) {
        buildings = buildings.plus(building)
        nBuildings++
    }

    public fun save(context : Context) {
        try {
            val file = File(context.filesDir, "appinfo.txt")
            val fileOutputStream = FileOutputStream(file)
            val nBuildingJSON = gson.toJson(AppInfo(nBuildings))
            fileOutputStream.write(nBuildingJSON.toByteArray())
            fileOutputStream.close()

            for (i in 0..<nBuildings) {
                val file = File(context.filesDir, "building$i.txt")
                val fileOutputStream = FileOutputStream(file)
                val buildingJSON = gson.toJson(buildings[i])
                fileOutputStream.write(buildingJSON.toByteArray())
                fileOutputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    public fun load(context: Context) : Boolean {

        try {
            val file = File(context.filesDir, "appinfo.txt")
            if (file.exists()) {
                val fileInputStream = FileInputStream(file)
                val data = fileInputStream.bufferedReader().use { it.readText() }
                fileInputStream.close()
                nBuildings = gson.fromJson(data, AppInfo::class.java).nBuildings
                buildings = emptyArray()

                for (i in 0..<nBuildings) {
                    val fileB = File(context.filesDir, "building$i.txt")
                    if(fileB.exists()){
                        val fileInputStreamB = FileInputStream(fileB)
                        val data = fileInputStreamB.bufferedReader().use { it.readText() }
                        fileInputStreamB.close()
                        val building : Building = gson.fromJson(data, Building::class.java)
                        buildings = buildings.plus(building)
                    }
                    else {
                        nBuildings = i
                    }
                }
            }
            else{
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }
}

//TEST

public class AppControllerTest {

    companion object{
        public fun test(context: Context)
        {
            val app : AppController = AppController()

            app.addBuilding(Building("Bulevar 12. feb 124B", 43.344453,21.868461,4,200.0,1974,1.0))
            app.addBuilding(Building("G.M. Lešjanina 41", 43.319522,21.890812,7,250.0,1980,2.0))
            app.addBuilding(Building("Branka Krsmanovića 43", 43.323394,21.920225,6,220.0,1985,2.0))
            app.addBuilding(Building("Jug Bogdanova 25", 43.314777,21.891676,4,190.0,1989,3.0))

            //Toast.makeText(context, "oce ${app.buildings.size}", Toast.LENGTH_LONG).show()

            app.save(context)

            val app2 = AppController()
            if(app2.load(context))
            {
                Toast.makeText(context, "oce ${app2.nBuildings}", Toast.LENGTH_LONG).show()
                for (b in app2.buildings){
                    Log.d("AKIKAKI", "test: ${b.address}")
                }

            }
            else{

                Toast.makeText(context, "nece", Toast.LENGTH_LONG).show()
            }
        }

    }

}