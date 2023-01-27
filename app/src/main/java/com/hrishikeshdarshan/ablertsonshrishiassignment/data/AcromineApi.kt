package com.hrishikeshdarshan.ablertsonshrishiassignment.data

import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.AcromineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcromineApi {

    @GET("dictionary.py")
    suspend fun getLongForms(
        @Query("sf") shortForm: String
    ): Response<AcromineResponse>
}