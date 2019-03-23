package com.figjam.hackathon2019.clinic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.figjam.hackathon2019.R
import com.figjam.hackathon2019.models.Clinic
import com.squareup.picasso.Picasso

class ClinicDetailActivity : AppCompatActivity() {

    private lateinit var clinic: Clinic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clinic_detail)
        if (intent.getParcelableExtra<Clinic?>(CLINIC) != null) {
            clinic = intent.getParcelableExtra(CLINIC)
        } else {
            throw NotImplementedError("Requires Clinic")
        }
        configureClinicDetails()
        configureServices()

    }

    private fun configureClinicDetails() {
        findViewById<TextView>(R.id.text_view_clinic_name).text = clinic.name
        findViewById<TextView>(R.id.text_view_street_address).text = clinic.streetAddress
        findViewById<TextView>(R.id.text_view_city_state_zip).text = clinic.getProvincialAddress()
        val clinicImage:ImageView = findViewById(R.id.image_view_clinic)
        Picasso.get().load(clinic.imageUrl).into(clinicImage)
    }

    private fun configureServices() {
        val map: MutableMap<Boolean, Int> = HashMap()
        map.put(true, View.VISIBLE)
        map.put(false, View.GONE)
        findViewById<View>(R.id.linear_layout_primary).visibility = map.get(clinic.hasPrimaryCare())!!
        findViewById<View>(R.id.linear_layout_dental).visibility = map.get(clinic.hasDental())!!
        findViewById<View>(R.id.linear_layout_mental).visibility = map.get(clinic.hasMentalHealthServices())!!
        findViewById<View>(R.id.linear_layout_chronic).visibility = map.get(clinic.hasChronic())!!
        findViewById<View>(R.id.linear_layout_acute).visibility = map.get(clinic.hasAcute())!!
        findViewById<View>(R.id.linear_layout_lab).visibility = map.get(clinic.hasLab())!!
        findViewById<View>(R.id.linear_layout_std).visibility = map.get(clinic.hasStdTesting())!!
    }

    companion object {
        private val CLINIC = "clinic"

        fun startActivity(context: Context, clinic: Clinic) {
            val intent = Intent(context, ClinicDetailActivity::class.java).apply {
                putExtra(CLINIC, clinic)
            }
            context.startActivity(intent)
        }
    }
}
