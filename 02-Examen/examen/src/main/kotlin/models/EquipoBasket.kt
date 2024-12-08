package models
import java.time.LocalDate

class EquipoBasket (
    var nombre: String,
    var ciudad: String,
    var fundado: LocalDate,
    var campeonatosGanados: Int,
    var activo: Boolean,
    var promedioPuntos: Double,
    var jugadores: MutableList<Jugador> = mutableListOf()
)