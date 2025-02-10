package com.example.examen02.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.R
import com.example.examen02.models.Jugador
import com.example.examen02.data.JugadorRepositorio
import java.util.*

class JugadorFormulario : AppCompatActivity() {
    private lateinit var jugadorRepositorio: JugadorRepositorio

    private lateinit var editTextNombre: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextEstatura: EditText
    private lateinit var editTextFechaIngreso: EditText
    private lateinit var editTextEsTitular: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugador_formulario)

        jugadorRepositorio = JugadorRepositorio(this)

        editTextNombre = findViewById(R.id.editTextNombreJugador)
        editTextEdad = findViewById(R.id.editTextEdadJugador)
        editTextEstatura = findViewById(R.id.editTextEstaturaJugador)
        editTextFechaIngreso = findViewById(R.id.editTextFechaIngresoJugador)
        editTextEsTitular = findViewById(R.id.editTextEsTitularJugador)
        btnGuardar = findViewById(R.id.btnGuardarJugador)

        val idEquipo = intent.getIntExtra("idEquipo", -1)
        val jugadorId = intent.getIntExtra("jugadorId", -1)
        if (jugadorId != -1) {
            cargarJugador(jugadorId)
        }

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                val nombre = editTextNombre.text.toString()
                val edad = editTextEdad.text.toString().toInt()
                val estatura = editTextEstatura.text.toString().toDouble()
                val esTitular = editTextEsTitular.text.toString().toBoolean()

                val jugador = Jugador(
                    id = if (jugadorId == -1) 0 else jugadorId,
                    idEquipo = idEquipo,
                    nombre = nombre,
                    edad = edad,
                    estatura = estatura,
                    fechaIngreso = Date(),
                    esTitular = esTitular
                )

                val resultado = if (jugadorId == -1) {
                    jugadorRepositorio.insertarJugador(jugador) > 0
                } else {
                    jugadorRepositorio.actualizarJugador(jugador) > 0
                }

                if (resultado) {
                    Toast.makeText(this, "Jugador guardado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar el jugador", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarJugador(jugadorId: Int) {
        val jugador = jugadorRepositorio.obtenerPorId(jugadorId)
        jugador?.let {
            editTextNombre.setText(it.nombre)
            editTextEdad.setText(it.edad.toString())
            editTextEstatura.setText(it.estatura.toString())
            editTextFechaIngreso.setText(it.fechaIngreso.toString())
            editTextEsTitular.setText(it.esTitular.toString())
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = editTextNombre.text.toString().trim()
        val edad = editTextEdad.text.toString().trim()
        val estatura = editTextEstatura.text.toString().trim()
        val fechaIngreso = editTextFechaIngreso.text.toString().trim()
        val esTitular = editTextEsTitular.text.toString().trim()

        return when {
            nombre.isEmpty() -> {
                editTextNombre.error = "El nombre es obligatorio"
                false
            }
            edad.isEmpty() -> {
                editTextEdad.error = "La edad es obligatoria"
                false
            }
            estatura.isEmpty() -> {
                editTextEstatura.error = "La estatura es obligatoria"
                false
            }
            fechaIngreso.isEmpty() -> {
                editTextFechaIngreso.error = "La fecha de ingreso es obligatoria"
                false
            }
            esTitular.isEmpty() -> {
                editTextEsTitular.error = "Debe ingresar si es o no titular"
                false
            }
            else -> true
        }
    }
}