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
    @GET("Atlet")
    fun getAllAtlet() : Call<List<AtletResponse>>

    @POST("Atlet")
    fun postAtlet(@Body atlet: AtletResponse) : Call<AtletResponse>
    @POST("CabangOlahraga")
    fun postCO(@Body co: COResponse) : Call<COResponse>
    @PUT("Atlet/{id}")
    fun putAtlet(@Path("id") id: Int, @Body atlet: AtletResponse) : Call<AtletResponse>

    @PUT("CabangOlahraga/{id}")
    fun putCO(@Path("id") id: Int, @Body co: COResponse) : Call<COResponse>

    @DELETE("Atlet/{id}")
    fun deleteAtlet(@Path("id") id: Int) : Call<AtletResponse>

    @DELETE("CabangOlahraga/{id}")
    fun deleteCO(@Path("id") id: Int) : Call<COResponse>


}