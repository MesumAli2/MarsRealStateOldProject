package com.example.marsrealstate.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class Item(
    @PrimaryKey()
    val id: Int = 0,
    @ColumnInfo(name = "type")
    val itemType: String,
    @ColumnInfo(name = "price")
    val itemPrice: Double,
    @ColumnInfo(name = "imgUrl")
    val imgUrl : String
    )

fun Item.getCurrenncyInstance(): String =
    NumberFormat.getCurrencyInstance().format(itemPrice)