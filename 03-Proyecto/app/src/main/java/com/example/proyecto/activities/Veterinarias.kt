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

class Veterinarias : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Lista de localizaciones
    private val localizaciones = listOf(
        LatLng(-0.2068260290653146, -78.50909171898275), // Veterinaria Salupets
        LatLng(-0.20716355273932674, -78.5079228918773), // Dr. V CONSULTORIO VETERINARIO
        LatLng(-0.2114624534475762, -78.50589570596276), // Clínica Veterinaria Rottweiler
        LatLng(-0.20361350443290546, -78.50715876352997), // Veterinaria Andreita
        LatLng(-0.19457389799075223, -78.50198980323451), // Amigos Inseparables Veterinaria Pet Shop
        LatLng(-0.19988153064174616, -78.4828005557562) // Hospital Veterinario Vetcarecenter

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veterinarias)

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtener el fragmento del mapa y solicitar la notificación cuando esté listo
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_veterinarias) as SupportMapFragment
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
        val nombres = listOf("Veterinaria Salupets", "Dr. V CONSULTORIO VETERINARIO", "Clínica Veterinaria Rottweiler", "Veterinaria Andreita", "Amigos Inseparables Veterinaria Pet Shop", "Hospital Veterinario Vetcarecenter")
        for ((index, localizacion) in localizaciones.withIndex()) {
            mMap.addMarker(MarkerOptions().position(localizacion).title(nombres[index]))
        }
    }
}