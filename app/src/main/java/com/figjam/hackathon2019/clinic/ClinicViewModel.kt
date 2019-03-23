package com.figjam.hackathon2019.clinic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.figjam.hackathon2019.models.Clinic

class ClinicViewModel(private var repository:ClinicRepository): ViewModel() {

    private var allClinics = ArrayList<Clinic>()
    val clinics = MutableLiveData<List<Clinic>>()
    val services = MutableLiveData<List<String>>()
    val selectedServices = MutableLiveData<List<String>>()

    private val selectedServiceKeys = ArrayList<String>()

    lateinit var selectedItems: BooleanArray

    fun getClinics() {
        repository.getAllClinics(onSuccess = {clinics ->
            allClinics = ArrayList(clinics)
            getServices(clinics)
            filterClinics()
        }, onError = {

        })
    }

    fun addSelectedService(index: Int) {
        selectedItems[index] = true
        selectedServiceKeys.add(services.value!![index])
        selectedServices.value = selectedServiceKeys
        filterClinics()
    }

    fun removeSelectedService(index: Int) {
        selectedItems[index] = false
        selectedServiceKeys.remove(services.value!![index])
        selectedServices.value = selectedServiceKeys
        filterClinics()
    }

    private fun filterClinics() {
        val filteredClinics = ArrayList<Clinic>()
        if (selectedServiceKeys.isNotEmpty()) {
            allClinics.forEach { clinic ->
                with(clinic) {
                    var found = false
                    services?.forEach { service ->
                        if (selectedServiceKeys.contains(service)) {
                           found = true
                        }
                    }
                    if (found) {
                        filteredClinics.add(clinic)
                    }
                }
            }
            this.clinics.value = filteredClinics
        } else {
            this.clinics.value = allClinics
        }
    }

    private fun getServices(clinics: List<Clinic>) {
        val services = ArrayList<String>()
        clinics.forEach {clinic ->
            clinic.services?.forEach { service ->
                services.add(service)
            }
        }
        val distinctServices = services.distinct()
        this.services.value = distinctServices
        selectedItems = BooleanArray(distinctServices.size)
    }



}