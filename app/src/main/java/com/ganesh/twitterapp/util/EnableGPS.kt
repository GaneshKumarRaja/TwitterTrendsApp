package com.ganesh.twitterapp.util

import android.Manifest
import android.app.Activity

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.*
import javax.inject.Inject


@Suppress("DEPRECATED_IDENTITY_EQUALS")
open class EnableGPS @Inject  constructor(private var activity: Activity, private var gpsDialog: GPSDialog) {


    private val requestLocationUpdate = 1


     fun hasGPSPermission(): Boolean {

        if (checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestLocationUpdate
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
            show()
            return false
        }

        return true
    }


    private fun show() {
        gpsDialog.showDialog()
    }

}