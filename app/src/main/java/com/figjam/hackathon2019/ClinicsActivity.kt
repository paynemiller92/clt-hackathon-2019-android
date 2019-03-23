package com.figjam.hackathon2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.figjam.hackathon2019.clinic.ClinicViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


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

        clinicViewModel.services.observe(this, Observer {
            fab.setOnClickListener {
                selectServices()
            }
        })



    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp()

    private fun selectServices() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Services ")
        builder.setMultiChoiceItems(clinicViewModel.services.value?.toTypedArray(), clinicViewModel.selectedItems
        ) { _, selectedItemId, isSelected ->
            if (isSelected) {
                clinicViewModel.addSelectedService(selectedItemId)
            } else {
                clinicViewModel.removeSelectedService(selectedItemId)
            }
        }.setPositiveButton("Done!", null)
        builder.create().show()
    }
}
