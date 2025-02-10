package com.example.deber_equipobasket.models

import java.util.Date

data class Equipo(
    val id: Int,
    var nombre: String,
    var ciudad: String,
    var fundado: Date,
    var campeonatosGanados: Int,
    var activo: Boolean,
    var promedioPuntos: Double,
) {
    override fun toString(): String {
        return "$nombre\n$ciudad\n$fundado\n$campeonatosGanados"
    }
}