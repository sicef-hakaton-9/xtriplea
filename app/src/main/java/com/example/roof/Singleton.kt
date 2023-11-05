package com.example.roof

import com.example.roof.models.AppController

object Singleton {
    val app: AppController = AppController()
    var selectedBuilding: Int = 0
}