package com.example.proyecto.basedatos

import java.util.Date

class Mascota (
    val id: Int,
    var nombre: String,
    var edad: Int,
    var raza: String,
    var sexo: Boolean,
    var peso: Double,
    var altura: Double,
    var entrenado: Boolean
) {
    override fun toString(): String {
        return "$nombre\n$edad\n$raza\n$sexo\n$peso\n$altura\n$entrenado"
    }
}