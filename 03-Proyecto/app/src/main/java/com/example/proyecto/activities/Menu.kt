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

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSalir = findViewById<Button>(R.id.btn_salir)
        val btnMascota = findViewById<Button>(R.id.btn_mascota)
        val btnVeterinarias = findViewById<Button>(R.id.btn_veterinarias)
        val btnLugares = findViewById<Button>(R.id.btn_lugares)
        val btnPetTinder = findViewById<Button>(R.id.btn_petTinder)

        btnSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnMascota.setOnClickListener {
            val intent = Intent(this, SaludMascota::class.java)
            startActivity(intent)
        }

        btnVeterinarias.setOnClickListener {
            val intent = Intent(this, Veterinarias::class.java)
            startActivity(intent)
        }

        btnLugares.setOnClickListener {
            val intent = Intent(this, LugaresPetFriendly::class.java)
            startActivity(intent)
        }

        btnPetTinder.setOnClickListener {
            val intent = Intent(this, PetTinder::class.java)
            startActivity(intent)
        }
    }
}