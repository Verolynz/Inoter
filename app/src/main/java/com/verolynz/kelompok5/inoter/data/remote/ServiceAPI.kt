package com.verolynz.kelompok5.inoter.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface ServiceAPI {
    @GET("CabangOlahraga")
    fun getAllCO() : Call<List<COResponse>>
    @GET("Atlet")
    fun getAllAtlet() : Call<List<AtletResponse>>
}