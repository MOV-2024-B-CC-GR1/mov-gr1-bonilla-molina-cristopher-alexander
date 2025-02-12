package com.example.proyecto.activities

import android.Manifest
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.proyecto.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.content.pm.PackageManager
import android.location.Location

class LugaresPetFriendly : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Lista de localizaciones
    private val localizaciones = listOf(
        LatLng(-0.20411966690448388, -78.48210328946189), // NOE sushi bar
        LatLng(-0.2020270819521312, -78.428103318298), // Boker Tov
        LatLng(-0.18457739397081274, -78.48071870480618), // Cosa Nostra
        LatLng(-0.17350042559857065, -78.48063660480626), // Pepitos Grill
        LatLng(-0.13305457969378068, -78.48667478946192), // Chios Burguer
        LatLng(-0.20570296692623227, -78.48080657597008) // Paccari Casa de Experiencias
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lugares_pet_friendly)

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtener el fragmento del mapa y solicitar la notificación cuando esté listo
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_lugares) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Cargar las localizaciones en el mapa
        cargarLocalizaciones()

        // Habilitar la ubicación del usuario
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permisos si no están concedidos
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        mMap.isMyLocationEnabled = true

        // Obtener la ubicación actual del usuario
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val currentLocation = LatLng(location.latitude, location.longitude)
                // Agregar un marcador en la ubicación actual del usuario
                mMap.addMarker(MarkerOptions().position(currentLocation).title("Tu Ubicación"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f)) // Mover la cámara a la ubicación del usuario
            }
        }

        // Habilitar los controles de zoom
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun cargarLocalizaciones() {
        // Agregar marcadores para cada localización
        val nombres = listOf("NOE sushi bar", "Boker Tov", "Cosa Nostra", "Pepitos Grill", "Chios Burguer", "Paccari Casa de Experiencias")
        for ((index, localizacion) in localizaciones.withIndex()) {
            mMap.addMarker(MarkerOptions().position(localizacion).title(nombres[index]))
        }
    }
}