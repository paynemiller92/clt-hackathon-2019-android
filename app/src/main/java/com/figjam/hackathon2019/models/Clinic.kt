package com.figjam.hackathon2019.models

data class Clinic(
    var city: String?,
    var faxNumber: String?,
    var fee: Double?,
    var isAppointmentOnly: Boolean?,
    var isSpanishSpeaking: Boolean?,
    var name: String?,
    var phoneNumber: String?,
    var services: List<String>?,
    var state: String?,
    var streetAddress: String?,
    var url: String?,
    var zipCode: String?,
    var latitude: Double?,
    var longitude: Double?
)