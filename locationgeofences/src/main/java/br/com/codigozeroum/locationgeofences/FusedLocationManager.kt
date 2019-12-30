package br.com.codigozeroum.locationgeofences

import android.content.Context
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class FusedLocationManager {

    companion object {

        private lateinit var locationCallback: LocationCallback
        private lateinit var fusedLocationClient: FusedLocationProviderClient

        fun fusedLocationProviderClientInitialize(
            context: Context
        ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fun startLocationUpdates(): Task<Void> = fusedLocationClient.requestLocationUpdates(
            LocationRequest(),
            locationCallback,
            null /* Looper */
        )

        fun setupLocationCallback() : LocationCallback{
            return object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                }
            }
        }

    }
}