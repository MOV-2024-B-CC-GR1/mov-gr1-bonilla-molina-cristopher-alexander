package services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.EquipoBasket
import models.Jugador
import java.io.File
import java.lang.reflect.Type

object CRUD {
    private val archivoEquipos = File("src/main/resources/equipos.json")
    private val gson = Gson()

    fun guardarEquiposEnArchivo(equipos: MutableList<EquipoBasket>) {
        val json = gson.toJson(equipos)
        archivoEquipos.writeText(json)
    }

    fun cargarEquiposDesdeArchivo(): MutableList<EquipoBasket> {
        if (!archivoEquipos.exists()) return mutableListOf()
        val json = archivoEquipos.readText()
        val tipoLista: Type = object : TypeToken<MutableList<EquipoBasket>>() {}.type
        return gson.fromJson(json, tipoLista) ?: mutableListOf()
    }

    fun crearEquipo(equipos: MutableList<EquipoBasket>, equipo: EquipoBasket) {
        equipos.add(equipo)
        guardarEquiposEnArchivo(equipos)
    }

    fun leerEquipos(equipos: MutableList<EquipoBasket>) {
        if (equipos.isEmpty()) {
            println("No hay equipos registrados.")
            return
        }

        equipos.forEachIndexed { index, equipo ->
            println("Equipo $index:")
            println("  Nombre: ${equipo.nombre}")
            println("  Ciudad: ${equipo.ciudad}")
            println("  Fundado: ${equipo.fundado}")
            println("  Campeonatos Ganados: ${equipo.campeonatosGanados}")
            println("  Promedio De Puntos: ${equipo.promedioPuntos}")
            println("  Activo: ${equipo.activo}")
            if (equipo.jugadores.isEmpty()) {
                println("  Jugadores: Sin registros.")
            } else {
                println("  Jugadores:")
                equipo.jugadores.forEachIndexed { jIndex, jugador ->
                    println("    Jugador $jIndex: ${jugador.nombre}, Edad: ${jugador.edad}, Estatura: ${jugador.estatura}, Fecha de Ingreso: ${jugador.fechaIngreso}, Titular: ${jugador.esTitular}")
                }
            }
        }
    }

    fun actualizarEquipo(equipos: MutableList<EquipoBasket>, index: Int, equipoActualizado: EquipoBasket) {
        if (index in equipos.indices) {
            val equipoExistente = equipos[index]
            equipoActualizado.jugadores = equipoExistente.jugadores // Conserva los jugadores actuales
            equipos[index] = equipoActualizado
            guardarEquiposEnArchivo(equipos)
        } else {
            println("¡El Índice No Existe!")
        }
    }

    fun eliminarEquipo(equipos: MutableList<EquipoBasket>, index: Int) {
        if (index in equipos.indices) {
            equipos.removeAt(index)
            guardarEquiposEnArchivo(equipos)
        } else {
            println("¡El Índice No Existe!")
        }
    }

    fun agregarJugadorAEquipo(equipos: MutableList<EquipoBasket>, equipoIndex: Int, jugador: Jugador) {
        if (equipoIndex in equipos.indices) {
            equipos[equipoIndex].jugadores.add(jugador)
            guardarEquiposEnArchivo(equipos)
        } else {
            println("Índice de equipo fuera de rango.")
        }
    }

    fun actualizarJugadorDeEquipo(equipos: MutableList<EquipoBasket>, equipoIndex: Int, jugadorIndex: Int, jugadorActualizado: Jugador) {
        if (equipoIndex in equipos.indices && jugadorIndex in equipos[equipoIndex].jugadores.indices) {
            equipos[equipoIndex].jugadores[jugadorIndex] = jugadorActualizado
            guardarEquiposEnArchivo(equipos)
        } else {
            println("¡El Índice No Existe!")
        }
    }

    fun eliminarJugadorDeEquipo(equipos: MutableList<EquipoBasket>, equipoIndex: Int, jugadorIndex: Int) {
        if (equipoIndex in equipos.indices && jugadorIndex in equipos[equipoIndex].jugadores.indices) {
            equipos[equipoIndex].jugadores.removeAt(jugadorIndex)
            guardarEquiposEnArchivo(equipos)
        } else {
            println("¡El Índice No Existe!")
        }
    }
}
