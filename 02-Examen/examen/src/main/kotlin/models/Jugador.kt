package models

import java.time.LocalDate

class Jugador (
    var nombre: String,
    var edad: Int,
    var estatura: Double,
    var fechaIngreso: LocalDate,
    var esTitular: Boolean
)