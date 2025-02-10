package com.example.deber_equipobasket.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.deber_equipobasket.models.Jugador
import java.text.SimpleDateFormat
import java.util.*

class JugadorRepositorio(context: Context) {
    private val dbHelper = BaseDeDatos(context)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun insertarJugador(jugador: Jugador): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_equipo", jugador.idEquipo)
            put("nombre", jugador.nombre)
            put("edad", jugador.edad)
            put("estatura", jugador.estatura)
            put("fechaIngreso", dateFormat.format(jugador.fechaIngreso))
            put("esTitular", jugador.esTitular)
        }
        return try {
            db.beginTransaction()
            val id = db.insert("Jugador", null, values)
            db.setTransactionSuccessful()
            id
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun eliminarJugador(id: Int): Int {
        val db = dbHelper.writableDatabase
        return try {
            db.beginTransaction()
            val result = db.delete("Jugador", "id = ?", arrayOf(id.toString()))
            db.setTransactionSuccessful()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            0
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun obtenerPorEquipo(idEquipo: Int): List<Jugador> {
        val jugadores = mutableListOf<Jugador>()
        val db = dbHelper.readableDatabase

        try {
            val cursor = db.query(
                "Jugador",
                null,
                "idEquipo = ?",
                arrayOf(idEquipo.toString()),
                null,
                null,
                null
            )
        if (cursor.moveToFirst()) {
            do {
                val jugador = Jugador(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    idEquipo = idEquipo,
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                    estatura = cursor.getDouble(cursor.getColumnIndexOrThrow("estatura")),
                    fechaIngreso = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaIngreso")))
                        ?: Date(),
                    esTitular = cursor.getString(cursor.getColumnIndexOrThrow("nombre")).toBoolean()
                )
                    jugadores.add(jugador)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return jugadores
    }

    fun obtenerPorId(id: Int): Jugador? {
        val db = dbHelper.readableDatabase
        var jugador: Jugador? = null

        try {
            val cursor = db.query(
                "Jugador",
                null,
                "id = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
            cursor.use {
                if (it.moveToFirst()) {
                    jugador = Jugador(
                        id = it.getInt(it.getColumnIndexOrThrow("id")),
                        idEquipo = it.getInt(it.getColumnIndexOrThrow("id_equipo")),
                        nombre = it.getString(it.getColumnIndexOrThrow("nombre")),
                        edad = it.getInt(it.getColumnIndexOrThrow("edad")),
                        estatura = it.getDouble(it.getColumnIndexOrThrow("estatura")),
                        fechaIngreso = dateFormat.parse(it.getString(cursor.getColumnIndexOrThrow("fechaIngreso"))) ?: Date(),
                        esTitular = it.getString(it.getColumnIndexOrThrow("nombre")).toBoolean()
                    )
                }
            }
        }catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return jugador
    }
    
    fun actualizarJugador(jugador: Jugador): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id_Equipo", jugador.idEquipo)
            put("nombre", jugador.nombre)
            put("edad", jugador.edad)
            put("estatura", jugador.estatura)
            put("fechaIngreso", dateFormat.format(jugador.fechaIngreso))
            put("esTitular", jugador.esTitular)
        }

        return try {
            db.beginTransaction()
            val rowsAffected = db.update(
                "Jugador",
                values,
                "id = ?",
                arrayOf(jugador.id.toString())
            )
            db.setTransactionSuccessful()
            rowsAffected 
        } catch (e: Exception) {
            e.printStackTrace()
            0 
        } finally {
            db.endTransaction()
            db.close()
        }
    }
}