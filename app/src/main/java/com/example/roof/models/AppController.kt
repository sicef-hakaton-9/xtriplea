package com.example.roof.models
import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

data class AppInfo(val nBuildings: Int)

class AppController {
    public var buildings : Array<Building> = emptyArray()
    private var nBuildings : Int = 0
    private val gson = Gson()

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
                        break
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
