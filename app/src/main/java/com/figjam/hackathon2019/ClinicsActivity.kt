package com.figjam.hackathon2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.figjam.hackathon2019.clinic.ClinicViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val clinicViewModel by viewModel<ClinicViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.mainNavigationFragment)

        val navGraph = navController.navInflater.inflate(R.navigation.navgraph)

        navController.graph = navGraph

        clinicViewModel.getClinics()

    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp()
}
