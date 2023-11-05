package com.example.roof.models

import java.io.Serializable

data class Position(val x: Double, val y: Double) : Serializable

class Building (
    public val address : String,
    public val lat : Double,
    public val lng : Double,
    public val nFloors : Int = 1,
    public val surfaceArea : Double,
    public val yearBuiltIn : Int,
    public val isolationQuality : Double, // range in 0-2,
    public val solarPanels : Boolean = false,
    public val parking : Boolean = false,
    public val garage : Boolean = false,
    public val lift : Boolean = false,
    public val greenPass: Boolean = true,
) : Serializable {

    public fun airQuality() : Double {

        //hard coded

        return 1.2 // range in 0-2
    }

    public fun noiseLevel() : Double {

        //hard coded

        return 1.6 // range in 0-2
    }

    public fun trafficFlow() : Double {

        //hard coded

        return  1.7 // range in 0-2
    }

    public fun score() : String {
        var s : Double = 1.0

        if (lift) s += 0.5
        if (garage) s += 0.9
        if (parking) s += 0.4
        if (solarPanels) s += 1.2

        s += isolationQuality
        s += airQuality()
        s += noiseLevel()

        return String.format("%.1f", s)
    }
}

