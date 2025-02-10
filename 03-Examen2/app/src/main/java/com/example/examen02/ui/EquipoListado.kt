package com.example.examen02.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.R
import com.example.examen02.data.EquipoRepositorio
import com.example.examen02.mapa.MapaActivity
import com.example.examen02.models.Equipo


class EquipoListado : AppCompatActivity() {
    private lateinit var equipoRepositorio: EquipoRepositorio
    private lateinit var lvEquipos: ListView
    private lateinit var btnAgregarEquipo: Button
    private var equipos = mutableListOf<Equipo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_listado)

        equipoRepositorio = EquipoRepositorio(this)
        lvEquipos = findViewById(R.id.lvEquipos)
        btnAgregarEquipo = findViewById(R.id.btnAgregarEquipo)

        // Cargar lista de equipos
        loadEquipos()

        // Configurar clic en el botón de agregar equipo
        btnAgregarEquipo.setOnClickListener {
            val intent = Intent(this, EquipoFormulario::class.java)
            startActivity(intent) // Abre la actividad para agregar una nueva equipo
        }

        // Configurar clic en un elemento de la lista
        lvEquipos.setOnItemClickListener { _, _, position, _ ->
            val equipoSeleccionada = equipos[position]
            mostrarOpciones(equipoSeleccionada)
        }
    }

    override fun onResume() {
        super.onResume()
        loadEquipos() // Recargar la lista al volver a esta actividad
    }

    private fun loadEquipos() {
        equipos.clear()
        equipos.addAll(equipoRepositorio.obtenerTodas())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Usamos el layout simple predeterminado
            equipos // `toString()` se usará automáticamente para cada elemento
        )
        lvEquipos.adapter = adapter
    }


    private fun mostrarOpciones(equipo: Equipo) {
        // Mostrar un cuadro de diálogo con las opciones Actualizar, Eliminar y Ver Jugadors
        val opciones = arrayOf("Actualizar", "Eliminar", "Ver Jugadores", "Mapa")
        AlertDialog.Builder(this)
            .setTitle("Opciones para ${equipo.nombre}")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> { // Actualizar
                        val intent = Intent(this, EquipoFormulario::class.java).apply {
                            putExtra("equipoId", equipo.id)
                        }
                        startActivity(intent)
                    }
                    1 -> { // Eliminar
                        confirmarEliminacion(equipo)
                    }
                    2 -> { // Ver Jugadores
                        val intent = Intent(this, JugadorListado::class.java).apply {
                            putExtra("equipoId", equipo.id)
                        }
                        startActivity(intent)
                    }
                    3 -> { // Ver Mapa
                        val intent = Intent(this, MapaActivity::class.java).apply {
                            putExtra("LATITUD", equipo.latitud)
                            putExtra("LONGITUD", equipo.longitud)
                            putExtra("nombre", equipo.nombre)
                        }
                        startActivity(intent)
                    }
                }
            }
            .show()
    }

    private fun confirmarEliminacion(equipo: Equipo) {
        // Mostrar un cuadro de confirmación para eliminar la equipo
        AlertDialog.Builder(this)
            .setTitle("Eliminar Equipo")
            .setMessage("¿Estás seguro de que deseas eliminar al equipo '${equipo.nombre}'?")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = equipoRepositorio.eliminarEquipo(equipo.id)
                if (resultado > 0) {
                    Toast.makeText(this, "Equipo eliminado", Toast.LENGTH_SHORT).show()
                    loadEquipos() // Recargar la lista
                } else {
                    Toast.makeText(this, "Error al eliminar al equipo", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}