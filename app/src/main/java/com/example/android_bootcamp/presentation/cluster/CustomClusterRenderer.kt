package com.example.android_bootcamp.presentation.cluster

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.example.android_bootcamp.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

class CustomClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MyClusterItem>
) : DefaultClusterRenderer<MyClusterItem>(context, map, clusterManager) {

    private val iconGenerator = IconGenerator(context)
    private val clusterSizeCache = HashMap<Int, Bitmap>()

    init {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(Color.BLUE)
        shape.minimumWidth
        shape.setStroke(2, Color.WHITE)

        iconGenerator.setBackground(shape)
        iconGenerator.setTextAppearance(R.style.ClusterTextStyle)
    }

    override fun onBeforeClusterItemRendered(item: MyClusterItem, markerOptions: MarkerOptions) {
        markerOptions.title(item.title)
            .snippet(item.snippet)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<MyClusterItem>, markerOptions: MarkerOptions) {
        val marker = getClusterBitmap(cluster.size)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(marker))
    }

    private fun getClusterBitmap(count: Int): Bitmap {
        return clusterSizeCache[count] ?: run {
            val bitmap = iconGenerator.makeIcon(count.toString())
            clusterSizeCache[count] = bitmap
            bitmap
        }
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MyClusterItem>): Boolean {
        return cluster.size > 1
    }
}