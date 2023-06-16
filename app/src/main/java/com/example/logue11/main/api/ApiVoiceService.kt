package com.example.logue11.main.api

import com.example.logue11.main.activity.speaking.SpeakingResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiVoiceService {

    @Multipart
    @POST("predict")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<SpeakingResponse>



}