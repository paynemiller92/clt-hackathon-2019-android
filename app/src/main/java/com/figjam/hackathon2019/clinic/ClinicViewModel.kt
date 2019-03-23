package com.figjam.hackathon2019.clinic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.figjam.hackathon2019.models.Clinic

class ClinicViewModel(private var repository:ClinicRepository): ViewModel() {

    val clinics = MutableLiveData<List<Clinic>>()

    fun getClinics() {
        repository.getAllClinics(onSuccess = {clinics ->
            this.clinics.value = clinics
        }, onError = {

        })
    }
}