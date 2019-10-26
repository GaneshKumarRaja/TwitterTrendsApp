package com.ganesh.twitterapp.util

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.provider.Settings
import com.ganesh.twitterapp.R
import javax.inject.Inject

open class GPSDialog @Inject constructor(var activity: Activity) {


    private var dialog: Dialog

    init {

        val builder = AlertDialog.Builder(activity)

        builder.setTitle(activity.getString(R.string.gps_title))
        builder.setMessage(activity.getString(R.string.gps_message))

        builder.setPositiveButton(activity.getString(R.string.enable_gps),
            { dialog, which ->
                activity.startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                    100
                )
            })


        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)

    }


    fun showDialog() {
        dialog.show()
    }


}