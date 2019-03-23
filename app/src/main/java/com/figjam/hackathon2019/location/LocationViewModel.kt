package com.figjam.hackathon2019.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class LocationViewModel : ViewModel() {

    val currentLocation = MutableLiveData<LatLng>()

}