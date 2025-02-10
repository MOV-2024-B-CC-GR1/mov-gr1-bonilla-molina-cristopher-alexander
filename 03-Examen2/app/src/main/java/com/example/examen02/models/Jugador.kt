package com.example.examen02.models

import java.util.Date

data class Jugador(
    val id: Int,
    var idEquipo: Int,
    var nombre: String,
    var edad: Int,
    var estatura: Double,
    var fechaIngreso: Date,
    var esTitular: Boolean
) {
    override fun toString(): String {
        return "$nombre\n$edad\n$estatura\n$fechaIngreso\n$esTitular"
    }
}