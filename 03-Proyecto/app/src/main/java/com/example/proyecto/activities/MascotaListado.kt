package com.example.proyecto.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.example.proyecto.basedatos.MascotaRepository
import com.example.proyecto.basedatos.Mascota

class MascotaListado : AppCompatActivity() {
    private lateinit var mascotaRepositorio: MascotaRepository
    private lateinit var lvMascotas: ListView
    private lateinit var btnAgregarMascota: Button
    private var mascotas = mutableListOf<Mascota>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_listado)

        mascotaRepositorio = MascotaRepository(this)
        lvMascotas = findViewById(R.id.lvMascotas)
        btnAgregarMascota = findViewById(R.id.btnIngresarMascota)

        // Cargar lista de mascotas
        loadMascotas()

        // Configurar clic en el botón de agregar mascota
        btnAgregarMascota.setOnClickListener {
            val intent = Intent(this, MascotaFormulario::class.java)
            startActivity(intent) // Abre la actividad para agregar una nueva mascota
        }

        // Configurar clic en un elemento de la lista
        lvMascotas.setOnItemClickListener { _, _, position, _ ->
            val mascotaSeleccionada = mascotas[position]
            mostrarOpciones(mascotaSeleccionada)
        }
    }

    override fun onResume() {
        super.onResume()
        loadMascotas() // Recargar la lista al volver a esta actividad
    }

    private fun loadMascotas() {
        mascotas.clear()
        mascotas.addAll(mascotaRepositorio.obtenerTodas())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Usamos el layout simple predeterminado
            mascotas // `toString()` se usará automáticamente para cada elemento
        )
        lvMascotas.adapter = adapter
    }


    private fun mostrarOpciones(mascota: Mascota) {
        // Mostrar un cuadro de diálogo con las opciones Actualizar, Eliminar y Ver Jugadors
        val opciones = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Opciones para ${mascota.nombre}")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> { // Actualizar
                        val intent = Intent(this, MascotaFormulario::class.java).apply {
                            putExtra("mascotaId", mascota.id)
                        }
                        startActivity(intent)
                    }
                    1 -> { // Eliminar
                        confirmarEliminacion(mascota)
                    }
                }
            }
            .show()
    }

    private fun confirmarEliminacion(mascota: Mascota) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Mascota")
            .setMessage("¿Estás seguro de que deseas eliminar la mascota '${mascota.nombre}'?")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = mascotaRepositorio.eliminarMascota(mascota.id)
                if (resultado > 0) {
                    Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show()
                    loadMascotas() // Recargar la lista
                } else {
                    Toast.makeText(this, "Error al eliminar la mascota", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}