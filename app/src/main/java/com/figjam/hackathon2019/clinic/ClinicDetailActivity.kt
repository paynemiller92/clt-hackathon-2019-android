package com.figjam.hackathon2019.clinic

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.figjam.hackathon2019.R
import com.figjam.hackathon2019.models.Clinic

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

    }

    companion object {
        private val CLINIC = "clinic"
    }

    private fun Intent.addClinic(clinic: Clinic) = putExtra(CLINIC, clinic)

    fun Context.startMyActivity(clinic: Clinic) =
        Intent(this, ClinicDetailActivity::class.java)
            .apply { addClinic(clinic) }.let(this::startActivity)
}
