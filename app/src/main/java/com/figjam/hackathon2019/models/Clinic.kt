package com.figjam.hackathon2019.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Clinic() : Parcelable {
    @SerializedName("city")
    var city: String? = null

    @SerializedName("faxNumber")
    var faxNumber: String? = null

    @SerializedName("fee")
    var fee: Double? = null

    @SerializedName("isAppointmentOnly")
    var isAppointmentOnly: Boolean? = null

    @SerializedName("isSpanishSpeaking")
    var isSpanishSpeaking: Boolean? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("services")
    var services: List<String>? = null

    @SerializedName("state")
    var state: String? = null

    @SerializedName("streetAddress")
    var streetAddress: String? = null

    @SerializedName("url'")
    var url: String? = null

    @SerializedName("zipCode")
    var zipCode: String? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    constructor(parcel: Parcel) : this() {
        city = parcel.readString()
        faxNumber = parcel.readString()
        fee = parcel.readValue(Double::class.java.classLoader) as? Double
        isAppointmentOnly = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        isSpanishSpeaking = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        name = parcel.readString()
        phoneNumber = parcel.readString()
        services = parcel.createStringArrayList()
        state = parcel.readString()
        streetAddress = parcel.readString()
        url = parcel.readString()
        zipCode = parcel.readString()
        latitude = parcel.readValue(Double::class.java.classLoader) as? Double
        longitude = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(city)
        parcel.writeString(faxNumber)
        parcel.writeValue(fee)
        parcel.writeValue(isAppointmentOnly)
        parcel.writeValue(isSpanishSpeaking)
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeStringList(services)
        parcel.writeString(state)
        parcel.writeString(streetAddress)
        parcel.writeString(url)
        parcel.writeString(zipCode)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clinic> {
        override fun createFromParcel(parcel: Parcel): Clinic {
            return Clinic(parcel)
        }

        override fun newArray(size: Int): Array<Clinic?> {
            return arrayOfNulls(size)
        }
    }

    fun getProvincialAddress(): String {
        return this.city + ", " + this.state + " " + this.zipCode
    }

    fun hasPrimaryCare(): Boolean {
        return hasService("Primary Medical Care")
    }

    fun hasDental(): Boolean {
        return hasService("Dental")
    }

    fun hasBehavioral(): Boolean {
        return hasService("Behavior Health")
    }

    fun hasPediatric(): Boolean {
        return hasService("Pediatric Care")
    }

    fun hasMentalHealthServices(): Boolean {
        return hasService("Mental Health Services")
    }

    fun hasChronic(): Boolean {
        return hasService("Chronic Disease Management")
    }

    fun hasAcute(): Boolean {
        return hasService("Acute Episodic Disease Management")
    }

    fun hasStdTesting(): Boolean {
        return hasService("STD Testing")
    }

    fun hasLab(): Boolean {
        return hasService("Lab Services")
    }

    private fun hasService(serviceName: String): Boolean {
        if (services != null) {
            for (service: String in services!!) {
                if (service.equals(serviceName, true)) {
                    return true
                }
            }
        }
        return false
    }
}
