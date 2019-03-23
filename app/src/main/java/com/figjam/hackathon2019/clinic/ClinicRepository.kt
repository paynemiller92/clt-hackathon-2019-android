package com.figjam.hackathon2019.clinic

import com.figjam.hackathon2019.models.Clinic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClinicRepository {
    fun getAllClinics(onSuccess: (List<Clinic>) -> Unit, onError: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://clt-hackathon-2019.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ClinicService::class.java)
            .getAllClinics()
            .enqueue(object: Callback<List<Clinic>> {
                override fun onFailure(call: Call<List<Clinic>>, t: Throwable) {
                    onError()
                }

                override fun onResponse(call: Call<List<Clinic>>, response: Response<List<Clinic>>) {
                    if (response.body().isNullOrEmpty()) {
                        onError()
                    } else {
                        onSuccess(response.body().orEmpty())
                    }
                }
            })
    }
}