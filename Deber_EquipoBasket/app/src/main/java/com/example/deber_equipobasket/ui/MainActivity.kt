package com.example.deber_equipobasket.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_equipobasket.R
import android.content.Intent
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private lateinit var btnVerEquipos: Button
    private lateinit var btnAgregarEquipo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnVerEquipos = findViewById(R.id.btnVerEquipos)
        btnAgregarEquipo = findViewById(R.id.btnAgregarEquipo)

        btnVerEquipos.setOnClickListener {
            val intent = Intent(this, EquipoListado::class.java)
            startActivity(intent)
        }

        btnAgregarEquipo.setOnClickListener {
            val intent = Intent(this, EquipoFormulario::class.java)
            startActivity(intent)
        }
    }
}