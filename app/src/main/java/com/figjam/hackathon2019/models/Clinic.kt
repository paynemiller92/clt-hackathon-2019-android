package com.figjam.hackathon2019.models

import android.os.Parcel
import android.os.Parcelable

class Clinic() : Parcelable {
    var city: String? = null
    var faxNumber: String? = null
    var fee: Double? = null
    var isAppointmentOnly: Boolean? = null
    var isSpanishSpeaking: Boolean? = null
    var name: String? = null
    var phoneNumber: String? = null
    var services: List<String>? = null
    var state: String? = null
    var streetAddress: String? = null
    var url: String? = null
    var zipCode: String? = null
    var latitude: Double? = null
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
}
