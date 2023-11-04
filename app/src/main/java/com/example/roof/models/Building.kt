package com.example.roof.models

data class Position(val x: Double, val y: Double)

class Building (
    public val address : String,
    public val location : Position,
    public val nFloors : Int = 1,
    public val surfaceArea : Double,
    public val yearBuiltIn : Int,
    public val isolationQuality : Double, // range in 0-2,
    public val solarPanels : Boolean = false,
    public val parking : Boolean = false,
    public val garage : Boolean = false,
    public val lift : Boolean = false,
) {

    private fun airQuality(location : Position) : Double {

        //hard coded

        return 1.2 // range in 0-2
    }

    private fun noiseLevel(location : Position) : Double {

        //hard coded

        return 1.6 // range in 0-2
    }

    public fun score() : Double {
        var s : Double = 1.0

        if (lift) s += 0.5
        if (garage) s += 0.9
        if (parking) s += 0.4
        if (solarPanels) s += 1.2

        s += isolationQuality
        s += airQuality(location)
        s += noiseLevel(location)

        return s
    }
}

