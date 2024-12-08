import services.CRUD
import models.EquipoBasket
import models.Jugador
import java.time.LocalDate

fun main() {
    val equipos = CRUD.cargarEquiposDesdeArchivo()

    println("Bienvenido al sistema CRUD de Equipos y Jugadores.")

    var opcionMenuPrincipal: Int

    do {
        println(
            """
            Menú Principal:
            1. Gestionar Equipos
            2. Gestionar Jugadores
            3. Salir
            Elija una opción:
            """.trimIndent()
        )

        opcionMenuPrincipal = readLine()?.toIntOrNull() ?: 0

        when (opcionMenuPrincipal) {
            1 -> gestionarEquipos(equipos)
            2 -> gestionarJugadores(equipos)
            3 -> println("Saliendo del programa...")
            else -> println("Opción inválida. Intente nuevamente.")
        }
    } while (opcionMenuPrincipal != 3)
}

fun gestionarEquipos(equipos: MutableList<EquipoBasket>) {
    var opcion: Int

    do {
        println(
            """
            Gestión de Equipos:
            1. Crear Equipo
            2. Leer Equipos
            3. Actualizar Equipo
            4. Eliminar Equipo
            5. Regresar al Menú Principal
            Elija una opción:
            """.trimIndent()
        )

        opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> {
                println("Ingrese el nombre del equipo:")
                val nombre = readLine() ?: ""
                println("Ingrese la ciudad del equipo:")
                val ciudad = readLine() ?: ""
                println("Ingrese la fecha de fundación del equipo (YYYY-MM-DD):")
                val fundado = LocalDate.parse(readLine() ?: "")
                println("Ingrese la cantidad de campeonatos ganados:")
                val campeonatosGanados = readLine()?.toIntOrNull() ?: 0
                println("¿El equipo está activo? (true/false):")
                val activo = readLine()?.toBoolean() ?: false

                val equipo = EquipoBasket(nombre, ciudad, fundado, campeonatosGanados, activo)
                CRUD.crearEquipo(equipos, equipo)
            }

            2 -> CRUD.leerEquipos(equipos)

            3 -> {
                println("Ingrese el índice del equipo a actualizar:")
                val index = readLine()?.toIntOrNull() ?: -1
                if (index in equipos.indices) {
                    println("Ingrese el nuevo nombre del equipo:")
                    val nombre = readLine() ?: ""
                    println("Ingrese la nueva ciudad del equipo:")
                    val ciudad = readLine() ?: ""
                    println("Ingrese la nueva fecha de fundación del equipo (YYYY-MM-DD):")
                    val fundado = LocalDate.parse(readLine() ?: "")
                    println("Ingrese la nueva cantidad de campeonatos ganados:")
                    val campeonatosGanados = readLine()?.toIntOrNull() ?: 0
                    println("¿El equipo está activo? (true/false):")
                    val activo = readLine()?.toBoolean() ?: false

                    val equipoActualizado = EquipoBasket(nombre, ciudad, fundado, campeonatosGanados, activo)
                    CRUD.actualizarEquipo(equipos, index, equipoActualizado)
                } else {
                    println("Índice fuera de rango.")
                }
            }

            4 -> {
                println("Ingrese el índice del equipo a eliminar:")
                val index = readLine()?.toIntOrNull() ?: -1
                CRUD.eliminarEquipo(equipos, index)
            }

            5 -> println("Regresando al Menú Principal...")
            else -> println("Opción inválida. Intente nuevamente.")
        }
    } while (opcion != 5)
}

fun gestionarJugadores(equipos: MutableList<EquipoBasket>) {
    var opcion: Int

    do {
        println(
            """
            Gestión de Jugadores:
            1. Agregar Jugador
            2. Leer Jugadores de un Equipo
            3. Actualizar Jugador
            4. Eliminar Jugador de un Equipo
            5. Regresar al Menú Principal
            Elija una opción:
            """.trimIndent()
        )

        opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> {
                println("Ingrese el índice del equipo al que desea agregar un jugador:")
                val equipoIndex = readLine()?.toIntOrNull() ?: -1
                if (equipoIndex in equipos.indices) {
                    println("Ingrese el nombre del jugador:")
                    val nombre = readLine() ?: ""
                    println("Ingrese la edad del jugador:")
                    val edad = readLine()?.toIntOrNull() ?: 0
                    println("Ingrese la estatura del jugador (en metros):")
                    val estatura = readLine()?.toDoubleOrNull() ?: 0.0
                    println("Ingrese la fecha de ingreso del jugador (YYYY-MM-DD):")
                    val fechaIngreso = LocalDate.parse(readLine() ?: "")
                    println("¿El jugador es titular? (true/false):")
                    val esTitular = readLine()?.toBoolean() ?: false

                    val jugador = Jugador(nombre, edad, estatura, fechaIngreso, esTitular)
                    CRUD.agregarJugadorAEquipo(equipos, equipoIndex, jugador)
                } else {
                    println("Índice de equipo fuera de rango.")
                }
            }

            2 -> {
                println("Ingrese el índice del equipo cuyos jugadores desea leer:")
                val equipoIndex = readLine()?.toIntOrNull() ?: -1
                if (equipoIndex in equipos.indices) {
                    println("Jugadores del equipo ${equipos[equipoIndex].nombre}:")
                    equipos[equipoIndex].jugadores.forEachIndexed { index, jugador ->
                        println("Jugador $index: ${jugador.nombre}, Edad: ${jugador.edad}, Estatura: ${jugador.estatura}, Fecha de Ingreso: ${jugador.fechaIngreso}, Titular: ${jugador.esTitular}")
                    }
                } else {
                    println("Índice fuera de rango.")
                }
            }

            3 -> {
                println("Ingrese el índice del equipo cuyos jugadores desea actualizar:")
                val equipoIndex = readLine()?.toIntOrNull() ?: -1
                if (equipoIndex in equipos.indices) {
                    println("Ingrese el índice del jugador a actualizar:")
                    val jugadorIndex = readLine()?.toIntOrNull() ?: -1
                    if (jugadorIndex in equipos[equipoIndex].jugadores.indices) {
                        println("Ingrese el nuevo nombre del jugador:")
                        val nombre = readLine() ?: ""
                        println("Ingrese la nueva edad del jugador:")
                        val edad = readLine()?.toIntOrNull() ?: 0
                        println("Ingrese la nueva estatura del jugador (en metros):")
                        val estatura = readLine()?.toDoubleOrNull() ?: 0.0
                        println("Ingrese la nueva fecha de ingreso del jugador (YYYY-MM-DD):")
                        val fechaIngreso = LocalDate.parse(readLine() ?: "")
                        println("¿El jugador es titular? (true/false):")
                        val esTitular = readLine()?.toBoolean() ?: false

                        val jugadorActualizado = Jugador(nombre, edad, estatura, fechaIngreso, esTitular)
                        CRUD.actualizarJugadorDeEquipo(equipos, equipoIndex, jugadorIndex, jugadorActualizado)
                    } else {
                        println("Índice de jugador fuera de rango.")
                    }
                } else {
                    println("Índice de equipo fuera de rango.")
                }
            }

            4 -> {
                println("Ingrese el índice del equipo del que desea eliminar un jugador:")
                val equipoIndex = readLine()?.toIntOrNull() ?: -1
                if (equipoIndex in equipos.indices) {
                    println("Ingrese el índice del jugador a eliminar:")
                    val jugadorIndex = readLine()?.toIntOrNull() ?: -1
                    CRUD.eliminarJugadorDeEquipo(equipos, equipoIndex, jugadorIndex)
                } else {
                    println("Índice de equipo fuera de rango.")
                }
            }

            5 -> println("Regresando al Menú Principal...")
            else -> println("Opción inválida. Intente nuevamente.")
        }
    } while (opcion != 5)
}
