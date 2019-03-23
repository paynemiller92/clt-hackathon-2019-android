package com.figjam.hackathon2019.koin

import com.figjam.hackathon2019.clinic.ClinicRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single{ ClinicRepository()}
}