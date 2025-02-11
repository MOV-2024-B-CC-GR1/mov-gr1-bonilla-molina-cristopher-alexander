package com.example.proyecto.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.MainActivity
import com.example.proyecto.R

class SaludMascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_salud_mascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnIngresar = findViewById<Button>(R.id.btnIngresarMascota)
        val btnListar = findViewById<Button>(R.id.btnListarMascotas)

        btnIngresar.setOnClickListener {
            val intent = Intent(this, MascotaFormulario::class.java)
            startActivity(intent)
        }

        btnListar.setOnClickListener {
            val intent = Intent(this, MascotaListado::class.java)
            startActivity(intent)
        }
    }
}