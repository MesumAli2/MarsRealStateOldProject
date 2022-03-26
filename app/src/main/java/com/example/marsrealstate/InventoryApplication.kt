package com.example.marsrealstate

import android.app.Application
import com.example.marsrealstate.database.ItemRoomDatabase

class InventoryApplication : Application(){
    val database : ItemRoomDatabase by lazy {
        ItemRoomDatabase.getDatabase(this)
    }
}