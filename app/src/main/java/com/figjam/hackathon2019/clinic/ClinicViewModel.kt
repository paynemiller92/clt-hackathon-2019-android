package com.figjam.hackathon2019.clinic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.figjam.hackathon2019.models.Clinic

class ClinicViewModel(private var repository:ClinicRepository): ViewModel() {

    private var allClinics = ArrayList<Clinic>()
    private val selectedServices = MutableLiveData<List<String>>()
    private val selectedServiceKeys = ArrayList<String>()
    private var feeLimit: Double = 1000.00

    val clinics = MutableLiveData<List<Clinic>>()
    val services = MutableLiveData<List<String>>()

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

    fun setFeeLimit(index: Int) {
        when (index) {
            0 -> feeLimit = 0.00
            1 -> feeLimit = 10.99
            else -> feeLimit = 1000.00
        }
        filterClinics()
    }

    private fun filterClinics() {
        var serviceFilteredClinics = ArrayList<Clinic>()
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
                        serviceFilteredClinics.add(clinic)
                    }
                }
            }
        } else {
            serviceFilteredClinics = allClinics
        }

        val filteredClinics = ArrayList<Clinic>()
        serviceFilteredClinics.forEach { clinic ->
            if (clinic.fee!! <= feeLimit) {
                filteredClinics.add(clinic)
            }
        }
        this.clinics.value = filteredClinics
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