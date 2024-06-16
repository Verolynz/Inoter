package com.verolynz.kelompok5.inoter.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServiceAPI {
    @GET("CabangOlahraga")
    fun getAllCO() : Call<List<COResponse>>

    // CRUD operations for CabangOlahraga
    @POST("CabangOlahraga")
    fun createCO(@Body co: COResponse): Call<COResponse>

    @PUT("CabangOlahraga/{id}")
    fun updateCO(@Path("id") id: String, @Body co: COResponse): Call<COResponse>

    @DELETE("CabangOlahraga/{id}")
    fun deleteCO(@Path("id") id: String): Call<Void>

    @GET("Atlet")
    fun getAllAtlet() : Call<List<AtletResponse>>

    @POST("Atlet")
    fun createAtlet(@Body atlet: AtletResponse): Call<AtletResponse>

    @PUT("Atlet/{id}")
    fun updateAtlet(@Path("id") id: String, @Body atlet: AtletResponse): Call<AtletResponse>

    @DELETE("Atlet/{id}")
    fun deleteAtlet(@Path("id") id: String): Call<Void>

}