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

        fun startActivity(context: Context, clinic: Clinic) {
            val intent = Intent(context, ClinicDetailActivity::class.java).apply {
                putExtra(CLINIC, clinic)
            }
            context.startActivity(intent)
        }
    }
}
