package com.example.crimeguardian.presentation

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterRenderer<T : ClusterItem>(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<T>?
) : DefaultClusterRenderer<T>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: T, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<T>): Boolean {
        // Show items as clusters only when zoomed out
        return cluster.size > 1
    }
}
