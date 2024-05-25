package com.example.rest

import com.google.gson.annotations.SerializedName

class MahasiswaResponse {
    @SerializedName("data")
    val data: List<Mahasiswa>? = null
    @SerializedName("status")
    val isStatus = false
}