package com.example.crimeguardian.presentation.incidents.fragment

import android.app.AlertDialog
import android.content.Context

class AlertDialogHelper(private val context: Context) {

    private var markerCountDialog: AlertDialog? = null

    fun showMarkerCountAlert(count: Int) {
        if (markerCountDialog != null && markerCountDialog!!.isShowing) {
            markerCountDialog?.setMessage("Number of markers: $count")
        } else {
            markerCountDialog = AlertDialog.Builder(context)
                .setTitle("Markers Around Selected Location")
                .setMessage("Number of markers: $count")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            markerCountDialog?.show()
        }
    }
}
