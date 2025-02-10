package com.example.examen02.models

import java.util.Date

data class Equipo(
    val id: Int,
    var nombre: String,
    var ciudad: String,
    var fundado: Date,
    var campeonatosGanados: Int,
    var activo: Boolean,
    var promedioPuntos: Double,
    var latitud: Double,
    var longitud: Double
) {
    override fun toString(): String {
        return "$nombre\n$ciudad\n$campeonatosGanados\n$activo\n$promedioPuntos"
    }
}