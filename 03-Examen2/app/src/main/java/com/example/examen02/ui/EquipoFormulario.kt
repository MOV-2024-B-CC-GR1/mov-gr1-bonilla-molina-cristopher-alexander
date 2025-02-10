package com.example.examen02.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.R
import com.example.examen02.data.EquipoRepositorio
import com.example.examen02.models.Equipo
import java.util.*

class EquipoFormulario : AppCompatActivity() {
    private lateinit var equipoRepositorio: EquipoRepositorio

    private lateinit var editTextNombre: EditText
    private lateinit var editTextCiudad: EditText
    private lateinit var editTextFundado: EditText
    private lateinit var editTextCampeonatosGanados: EditText
    private lateinit var editTextActivo: EditText
    private lateinit var editTextPromedioPuntos: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_formulario)

        equipoRepositorio = EquipoRepositorio(this)

        editTextNombre = findViewById(R.id.editTextNombreEquipo)
        editTextCiudad = findViewById(R.id.editTextCiudadEquipo)
        editTextFundado = findViewById(R.id.editTextFundadoEquipo)
        editTextCampeonatosGanados = findViewById(R.id.editTextCampeonatosGanadosEquipo)
        editTextActivo = findViewById(R.id.editTextActivoEquipo)
        editTextPromedioPuntos = findViewById(R.id.editTextPromedioPuntosEquipo)
        btnGuardar = findViewById(R.id.btnGuardarEquipo)

        val equipoId = intent.getIntExtra("equipoId", -1)
        if (equipoId != -1) {
            cargarEquipo(equipoId)
        }

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                val nombre = editTextNombre.text.toString()
                val ciudad = editTextCiudad.text.toString()
                //val fundado = editTextFundado.text.toString()
                val campeonatosGanados = editTextCampeonatosGanados.text.toString().toInt()
                val activo = editTextActivo.text.toString().toBoolean()
                val promedioPuntos = editTextPromedioPuntos.text.toString().toDouble()

                val equipo = Equipo(
                    id = if (equipoId == -1) 0 else equipoId, // Si es nuevo, ID será 0
                    nombre = nombre,
                    ciudad = ciudad,
                    fundado = Date(),
                    campeonatosGanados = campeonatosGanados,
                    activo = activo,
                    promedioPuntos = promedioPuntos
                )

                val resultado = if (equipoId == -1) {
                    equipoRepositorio.insertarEquipo(equipo) > 0 // Verifica si la inserción fue exitosa
                } else {
                    equipoRepositorio.actualizarEquipo(equipo) > 0 // Verifica si se actualizaron filas
                }

                if (resultado) {
                    Toast.makeText(this, "Equipo guardado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar el equipo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarEquipo(equipoId: Int) {
        val equipo = equipoRepositorio.obtenerPorId(equipoId)
        equipo?.let {
            editTextNombre.setText(it.nombre)
            editTextCiudad.setText(it.ciudad)
            editTextFundado.setText(it.fundado.toString())
            editTextCampeonatosGanados.setText(it.campeonatosGanados.toString())
            editTextActivo.setText(it.activo.toString())
            editTextPromedioPuntos.setText(it.promedioPuntos.toString())
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = editTextNombre.text.toString().trim()
        val ciudad = editTextCiudad.text.toString().trim()
        val fundado = editTextFundado.text.toString().trim()
        val campeonatosGanados = editTextCampeonatosGanados.text.toString().trim()
        val activo = editTextActivo.text.toString().trim()
        val promedioPuntos = editTextPromedioPuntos.text.toString().trim()

        return when {
            nombre.isEmpty() -> {
                editTextNombre.error = "El nombre es obligatorio"
                false
            }
            ciudad.isEmpty() -> {
                editTextCiudad.error = "La ciudad es obligatoria"
                false
            }
            fundado.isEmpty() -> {
                editTextFundado.error = "La fecha de fundación es obligatoria"
                false
            }
            campeonatosGanados.isEmpty() -> {
                editTextCampeonatosGanados.error = "Los campeonatos ganados son obligatorios"
                false
            }
            activo.isEmpty() -> {
                editTextActivo.error = "Saber si está activo es obligatorio"
                false
            }
            promedioPuntos.isEmpty() -> {
                editTextPromedioPuntos.error = "El promedio de puntos es obligatorio"
                false
            }
            else -> true
        }
    }
}