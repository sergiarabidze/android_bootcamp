package com.example.android_bootcamp.presentation.features.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.Global.putString
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.common.BaseFragment
import com.example.android_bootcamp.data.remote.dto.LocationDto
import com.example.android_bootcamp.data.remote.http.Resource
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.presentation.cluster.CustomClusterRenderer
import com.example.android_bootcamp.presentation.cluster.MyClusterItem
import com.example.android_bootcamp.presentation.features.bottomsheet.LocationDetails
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment :
    BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate),
    OnMapReadyCallback {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var clusterManager: ClusterManager<MyClusterItem>

    override fun setUp() {
        super.setUp()
        requestPermissions()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        addCurrentLocationMarker()
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
            addCurrentLocationMarker()
        } else {
            requestPermissions()
        }
        setObservers()
        if (!(::clusterManager.isInitialized)){
            setupClusterManager()
        }

    }

    override fun setListeners() {
        super.setListeners()
        binding.btnZoomIn.setOnClickListener {
            zoomIn()
        }
        binding.btnZoomOut.setOnClickListener {
            zoomOut()
        }
    }

    private fun setObservers() {
        viewModel.locations.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> handleSuccessState(resource.data)
                    is Resource.Error -> handleErrorState(resource.message)
                    Resource.Loading -> handleLoadingState()
                    Resource.Idle -> Unit
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSuccessState(data: List<LocationDto>) {
        binding.progressBar.visibility = View.GONE
        addMarkers(data)
    }

    private fun handleErrorState(message: String?) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(requireContext(), message ?: "Unknown error", Toast.LENGTH_SHORT).show()
    }

    private fun handleLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun addCurrentLocationMarker() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val currentLatLng = LatLng(it.latitude, it.longitude)

                        googleMap.addMarker(
                            MarkerOptions()
                                .position(currentLatLng)
                                .title("Your Location")
                        )
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLatLng,
                                15f
                            )
                        )
                    } ?: run {
                        Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            gpsEnabled || networkEnabled
        }
    }

    private fun promptForLocationSettings() {
        if (!isLocationEnabled(requireContext())) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun requestPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    initializeMap()
                    promptForLocationSettings()
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    initializeMap()
                    promptForLocationSettings()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Location permission required",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initializeMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun setupClusterManager() {
        clusterManager = ClusterManager<MyClusterItem>(requireContext(), googleMap)
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)

        clusterManager.setOnClusterItemClickListener { item ->
            showMarkerInfoBottomSheet(item.title, item.snippet)
            true
        }

        clusterManager.renderer = CustomClusterRenderer(
            requireContext(),
            googleMap,
            clusterManager
        )
    }

    private fun zoomIn() {
        googleMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    private fun zoomOut() {
        googleMap.animateCamera(CameraUpdateFactory.zoomOut())
    }

    private fun addMarkers(locations: List<LocationDto>) {
        clusterManager.clearItems()

        locations.forEach { location ->
            val position = LatLng(location.lat, location.lan)
            val clusterItem = MyClusterItem(
                position,
                location.title,
                location.address
            )
            clusterManager.addItem(clusterItem)
        }
        clusterManager.cluster()
    }

    private fun showMarkerInfoBottomSheet(title: String, address: String) {
        val args = Bundle().apply {
            putString("title", title)
            putString("address", address)
        }
        findNavController().navigate(
            R.id.action_payment_to_locationDetails,
            args
        )
    }


}