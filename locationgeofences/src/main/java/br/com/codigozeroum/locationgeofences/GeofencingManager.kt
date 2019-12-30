package br.com.codigozeroum.locationgeofences

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofencingManager {

    companion object {
        private lateinit var geofencingClient: GeofencingClient
        private var geofenceList = ArrayList<Geofence>()
        private lateinit var geofencePendingIntent: PendingIntent

        fun startGeofencing(context: Context){
            geofencingClient = LocationServices.getGeofencingClient(context)
        }

        fun buildGeofencingPendingIntent(context: Context, broadcastReceiverClass: Class<Any>): PendingIntent{
            val intent = Intent(context, broadcastReceiverClass::class.java)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        fun createGeofenceItem(
            geofenceId: String,
            latitude: Double,
            longitude: Double,
            radiusInMeters: Float,
            expirationInMiliseconds: Long,
            transitionTypes: List<Int>,
            loiteringDelay: Int?
            ): Geofence{
            return  Geofence.Builder()
                .setRequestId(geofenceId)
                .setCircularRegion(latitude, longitude, radiusInMeters)
                .setLoiteringDelay(loiteringDelay ?: 0)
                .setExpirationDuration(expirationInMiliseconds)
                .setTransitionTypes(transitionTypes[0])
                .build()
        }

        fun buildGeofenceListReferences(geofenceList: ArrayList<Geofence>){
            this.geofenceList.addAll(geofenceList)
        }

        fun buildGeofenceRequest(initialTrigger: Int, geofenceList: ArrayList<Geofence>): GeofencingRequest {
            return GeofencingRequest.Builder().apply {
                setInitialTrigger(initialTrigger)
                addGeofences(geofenceList)
            }.build()
        }

        fun setGeofences(geofencingRequest: GeofencingRequest, geofecingIntent: PendingIntent){
            geofencingClient.addGeofences(geofencingRequest, geofecingIntent).run {
                addOnSuccessListener {
                    Log.e("addOnSuccessListener", "Geofences successfuly added!")
                }
                addOnFailureListener {
                    Log.e("addOnFailureListener", "Geofences failure to add!")
                }
            }
        }

    }
}