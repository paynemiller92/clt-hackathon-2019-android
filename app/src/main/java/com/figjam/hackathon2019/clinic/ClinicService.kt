package com.figjam.hackathon2019.clinic

import com.figjam.hackathon2019.models.Clinic
import retrofit2.Call
import retrofit2.http.GET

interface ClinicService {
    @GET("api/clinic")
    fun getAllClinics(): Call<List<Clinic>>
}