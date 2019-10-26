package com.ganesh.twitterapp.util

import android.Manifest
import android.app.Activity

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject


open class EnableGPS public @Inject constructor(var activity: Activity, var gpsDialog: GPSDialog) {


    private val REQUEST_LOCATION_PERMISSION = 1


    internal fun hasGPSPermission(): Boolean {

        if (ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )

            return false

        } else {
            // GPS permission is granted
            return true
        }
    }


    fun isEnabledGPS(): Boolean {

        val service = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            enableGpS()
            return false
        }

        return true
    }


    fun enableGpS() {
        gpsDialog.showDialog()
    }

}