package com.example.examen02.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.examen02.models.Equipo
import java.text.SimpleDateFormat
import java.util.*

class EquipoRepositorio(context: Context) {
    private val dbHelper = BaseDeDatos(context)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun insertarEquipo(equipo: Equipo): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", equipo.nombre)
            put("ciudad", equipo.ciudad)
            put("fundado", dateFormat.format(equipo.fundado))
            put("campeonatosGanados", equipo.campeonatosGanados)
            put("activo", equipo.activo)
            put("promedioPuntos", equipo.promedioPuntos)
        }
        val result = db.insert("Equipo", null, values)
        db.close()
        return result
    }

    fun actualizarEquipo(equipo: Equipo): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", equipo.nombre)
            put("ciudad", equipo.ciudad)
            put("fundado", dateFormat.format(equipo.fundado))
            put("campeonatosGanados", equipo.campeonatosGanados)
            put("activo", equipo.activo)
            put("promedioPuntos", equipo.promedioPuntos)
        }
        val result = db.update("Equipo", values, "id = ?", arrayOf(equipo.id.toString()))
        db.close()
        return result
    }

    fun eliminarEquipo(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete("Equipo", "id = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun obtenerTodas(): List<Equipo> {
        val equipos = mutableListOf<Equipo>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Equipo", null)

        if (cursor.moveToFirst()) {
            do {
                val equipo = Equipo(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    ciudad = cursor.getString(cursor.getColumnIndexOrThrow("ciudad")),
                    fundado = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fundado"))) ?: Date(),
                    campeonatosGanados = cursor.getInt(cursor.getColumnIndexOrThrow("campeonatosGanados")),
                    activo = cursor.getString(cursor.getColumnIndexOrThrow("activo")).toBoolean(),
                    promedioPuntos = cursor.getDouble(cursor.getColumnIndexOrThrow("promedioPuntos"))
                )
                equipos.add(equipo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return equipos
    }

    fun obtenerPorId(id: Int): Equipo? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Equipo WHERE id = ?", arrayOf(id.toString()))
        var equipo: Equipo? = null

        if (cursor.moveToFirst()) {
            equipo = Equipo(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                ciudad = cursor.getString(cursor.getColumnIndexOrThrow("ciudad")),
                fundado = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("fundado"))) ?: Date(),
                campeonatosGanados = cursor.getInt(cursor.getColumnIndexOrThrow("campeonatosGanados")),
                activo = cursor.getString(cursor.getColumnIndexOrThrow("activo")).toBoolean(),
                promedioPuntos = cursor.getDouble(cursor.getColumnIndexOrThrow("promedioPuntos"))
            )
        }

        cursor.close()
        db.close()
        return equipo
    }
}