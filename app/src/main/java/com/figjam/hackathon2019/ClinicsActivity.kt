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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.ArrayAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val clinicViewModel by viewModel<ClinicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


        navController = Navigation.findNavController(this, R.id.mainNavigationFragment)

        val navGraph = navController.navInflater.inflate(R.navigation.navgraph)

        navController.graph = navGraph

        clinicViewModel.getClinics()

        fab.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        clinicViewModel.services.observe(this, Observer {
            servicesFilterButton.setOnClickListener {
                sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                selectServices()
            }

            feesFilterButton.setOnClickListener {
                sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                selectFees()
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

    private fun selectFees() {
        val builderSingle = AlertDialog.Builder(this)
        builderSingle.setTitle("Select Fee Range")
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        arrayAdapter.add("Free")
        arrayAdapter.add("< $11.00")
        arrayAdapter.add("< $15.00")
        builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }

        builderSingle.setAdapter(arrayAdapter) { _, which ->
           clinicViewModel.setFeeLimit(which)
        }
        builderSingle.show()
    }

}
