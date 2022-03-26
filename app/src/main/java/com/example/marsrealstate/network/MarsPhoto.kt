package com.example.marsrealstate.network

import com.squareup.moshi.Json

data class MarsPhoto (
    val price: String, val id: String, val type : String, @Json(name = "img_src") val imgSrcUrl: String
)