package com.figjam.hackathon2019.koin

import com.figjam.hackathon2019.clinic.ClinicViewModel
import com.figjam.hackathon2019.location.LocationViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { LocationViewModel() }
    viewModel { ClinicViewModel(get()) }
}