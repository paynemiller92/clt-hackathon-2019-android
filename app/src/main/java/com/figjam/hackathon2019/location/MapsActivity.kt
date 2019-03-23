package com.figjam.hackathon2019.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.figjam.hackathon2019.R
import com.figjam.hackathon2019.clinic.ClinicDetailActivity
import com.figjam.hackathon2019.clinic.ClinicRepository
import com.figjam.hackathon2019.clinic.ClinicViewModel
import com.figjam.hackathon2019.models.Clinic
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MapFragment : Fragment(), OnMapReadyCallback {

    var googleMap: GoogleMap? = null
    var clinicMarkerMap: MutableMap<Marker?, Clinic>? = HashMap()

    private val locationViewModel by viewModel<LocationViewModel>()
    private val clinicViewModel by sharedViewModel<ClinicViewModel>()


    companion object {
        const val LOCATION_PERMISSION = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        getLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        locationViewModel.currentLocation.observe(this, Observer { coordinate ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate))

            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(coordinate.latitude, coordinate.longitude))
                    .title("My Location")
            )
            googleMap.setOnInfoWindowClickListener { marker ->
                val clinic: Clinic? = clinicMarkerMap?.get(marker)
                if (clinic != null) {
                    ClinicDetailActivity.startActivity(context!!, clinic)
                }
                false
            }
        })

        clinicViewModel.clinics.observe(this, Observer { clinics ->
            markClinics(clinics)
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocation()
                }
                return
            }
            else -> { }
        }
    }

    private fun getLocation() {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                locationViewModel.currentLocation.value = LatLng(location.latitude, location.longitude)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }

        if (checkLocationPermission()) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION)
        }

    }


    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun markClinics(clinics: List<Clinic>) {
        for (clinic: Clinic in clinics) {
            val marker: Marker? = googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(clinic.latitude!!, clinic.longitude!!))
                    .title(clinic.name)
            )
            clinicMarkerMap?.put(marker, clinic)
        }
    }
}
