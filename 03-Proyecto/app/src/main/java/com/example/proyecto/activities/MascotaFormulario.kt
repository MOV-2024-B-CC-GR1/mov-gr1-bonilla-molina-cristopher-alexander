package com.example.proyecto.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.example.proyecto.basedatos.MascotaRepository
import com.example.proyecto.basedatos.Mascota
import java.util.*

class MascotaFormulario : AppCompatActivity() {
    private lateinit var mascotaRepository: MascotaRepository

    private lateinit var eTNombre: EditText
    private lateinit var eTEdad: EditText
    private lateinit var eTRaza: EditText
    private lateinit var eTSexo: EditText
    private lateinit var eTPeso: EditText
    private lateinit var eTAltura: EditText
    private lateinit var eTEntrenado: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_formulario)

        mascotaRepository = MascotaRepository(this)

        eTNombre = findViewById(R.id.eTNombreMascota)
        eTEdad = findViewById(R.id.eTEdadMascota)
        eTRaza = findViewById(R.id.eTRazaMascota)
        eTSexo = findViewById(R.id.eTSexoMascota)
        eTPeso = findViewById(R.id.eTPesoMascota)
        eTAltura = findViewById(R.id.eTAlturaMascota)
        eTEntrenado = findViewById(R.id.eTEntrenadoMascota)
        btnGuardar = findViewById(R.id.btnGuardarMascota)

        val mascotaId = intent.getIntExtra("mascotaId", -1)
        if (mascotaId != -1) {
            cargarMascota(mascotaId)
        }

        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                val nombre = eTNombre.text.toString()
                val edad = eTEdad.text.toString().toInt()
                val raza = eTRaza.text.toString()
                val sexo = eTSexo.text.toString().toBoolean()
                val peso = eTPeso.text.toString().toDoubleOrNull() ?: 0.0
                val altura = eTAltura.text.toString().toDoubleOrNull() ?: 0.0
                val entrenado = eTEntrenado.text.toString().toBoolean()

                val mascota = Mascota(
                    id = if (mascotaId == -1) 0 else mascotaId, // Si es nuevo, ID será 0
                    nombre = nombre,
                    edad = edad,
                    raza = raza,
                    sexo = sexo,
                    peso = peso,
                    altura = altura,
                    entrenado = entrenado
                )

                val resultado = if (mascotaId == -1) {
                    mascotaRepository.insertarMascota(mascota) > 0
                } else {
                    mascotaRepository.actualizarMascota(mascota) > 0
                }

                if (resultado) {
                    Toast.makeText(this, "Mascota guardada con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar la mascota", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarMascota(mascotaId: Int) {
        val mascota = mascotaRepository.obtenerPorId(mascotaId)
        mascota?.let {
            eTNombre.setText(it.nombre)
            eTEdad.setText(it.edad.toString())
            eTRaza.setText(it.raza)
            eTSexo.setText(it.sexo.toString())
            eTPeso.setText(it.peso.toString())
            eTAltura.setText(it.altura.toString())
            eTEntrenado.setText(it.entrenado.toString())
        }
    }

    private fun validarCampos(): Boolean {
        val nombre = eTNombre.text.toString().trim()
        val edad = eTEdad.text.toString().trim()
        val raza = eTRaza.text.toString().trim()
        val sexo = eTSexo.text.toString().trim()
        val peso = eTPeso.text.toString().trim()
        val altura = eTAltura.text.toString().trim()
        val entrenado = eTEntrenado.text.toString().trim()

        return when {
            nombre.isEmpty() -> {
                eTNombre.error = "El nombre es obligatorio"
                false
            }
            edad.isEmpty() -> {
                eTEdad.error = "La edad es obligatoria"
                false
            }
            raza.isEmpty() -> {
                eTRaza.error = "La raza es obligatoria"
                false
            }
            sexo.isEmpty() -> {
                eTSexo.error = "El sexo es obligatorio"
                false
            }
            peso.isEmpty() -> {
                eTPeso.error = "El peso es obligatorio"
                false
            }
            altura.isEmpty() -> {
                eTAltura.error = "La alturia es obligatoria"
                false
            }
            entrenado.isEmpty() -> {
                eTEntrenado.error = "Conocer si está entrenado es obligatorio"
                false
            }
            else -> true
        }
    }
}