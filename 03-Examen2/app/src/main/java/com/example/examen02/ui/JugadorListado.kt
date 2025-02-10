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
import com.example.examen02.models.Jugador
import com.example.examen02.data.JugadorRepositorio

class JugadorListado : AppCompatActivity() {
    private lateinit var jugadorRepositorio: JugadorRepositorio
    private lateinit var lvJugadores: ListView
    private lateinit var btnAgregarJugador: Button
    private var jugadores = mutableListOf<Jugador>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugador_listado)

        jugadorRepositorio = JugadorRepositorio(this)
        lvJugadores = findViewById(R.id.lvJugadores)
        btnAgregarJugador = findViewById(R.id.btnAgregarJugador)

        val idEquipo = intent.getIntExtra("idEquipo", -1)
        loadJugadores(idEquipo)

        btnAgregarJugador.setOnClickListener {
            val intent = Intent(this, JugadorFormulario::class.java).apply {
                putExtra("idEquipo", idEquipo)
            }
            startActivity(intent)
        }

        lvJugadores.setOnItemClickListener { _, _, position, _ ->
            val jugadorSeleccionado = jugadores[position]
            mostrarOpciones(jugadorSeleccionado, idEquipo)
        }
    }

    override fun onResume() {
        super.onResume()
        val idEquipo = intent.getIntExtra("idEquipo", -1)
        loadJugadores(idEquipo)
    }

    private fun loadJugadores(idEquipo: Int) {
        jugadores.clear()
        jugadores.addAll(jugadorRepositorio.obtenerPorEquipo(idEquipo))

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            jugadores
        )
        lvJugadores.adapter = adapter
    }


    private fun mostrarOpciones(jugador: Jugador, idEquipo: Int) {
        val opciones = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Opciones para ${jugador.nombre}")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> { // Actualizar
                        val intent = Intent(this, JugadorFormulario::class.java).apply {
                            putExtra("jugadorId", jugador.id)
                            putExtra("idEquipo", idEquipo)
                        }
                        startActivity(intent)
                    }
                    1 -> { // Eliminar
                        confirmarEliminacion(jugador, idEquipo)
                    }
                }
            }
            .show()
    }

    private fun confirmarEliminacion(jugador: Jugador, equipoId: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Jugador")
            .setMessage("¿Estás seguro de que deseas eliminar al jugador '${jugador.nombre}'?")
            .setPositiveButton("Sí") { _, _ ->
                val resultado = jugadorRepositorio.eliminarJugador(jugador.id)
                if (resultado > 0) {
                    Toast.makeText(this, "Jugador eliminado", Toast.LENGTH_SHORT).show()
                    loadJugadores(equipoId)
                } else {
                    Toast.makeText(this, "Error al eliminar el jugador", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}