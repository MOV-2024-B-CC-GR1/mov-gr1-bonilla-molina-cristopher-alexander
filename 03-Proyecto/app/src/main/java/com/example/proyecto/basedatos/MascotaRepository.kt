package com.example.proyecto.basedatos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.proyecto.basedatos.Mascota
import java.text.SimpleDateFormat
import java.util.*

class MascotaRepository (context: Context) {
    private val dbHelper = BaseDatos(context)

    fun insertarMascota(mascota: Mascota): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", mascota.nombre)
            put("edad", mascota.edad)
            put("raza", mascota.raza)
            put("sexo", mascota.sexo)
            put("peso", mascota.peso)
            put("altura", mascota.altura)
            put("entrenado", mascota.entrenado)
        }
        val result = db.insert("Mascota", null, values)
        db.close()
        return result
    }

    fun actualizarMascota(mascota: Mascota): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", mascota.nombre)
            put("edad", mascota.edad)
            put("raza", mascota.raza)
            put("sexo", mascota.sexo)
            put("peso", mascota.peso)
            put("altura", mascota.altura)
            put("entrenado", mascota.entrenado)
        }
        val result = db.update("Mascota", values, "id = ?", arrayOf(mascota.id.toString()))
        db.close()
        return result
    }

    fun eliminarMascota(id: Int): Int {
        val db = dbHelper.writableDatabase
        val result = db.delete("Mascota", "id = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun obtenerTodas(): List<Mascota> {
        val mascotas = mutableListOf<Mascota>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Mascota", null)

        if (cursor.moveToFirst()) {
            do {
                val mascota = Mascota(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                    raza = cursor.getString(cursor.getColumnIndexOrThrow("raza")),
                    sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo")).toBoolean(),
                    peso = cursor.getDouble(cursor.getColumnIndexOrThrow("peso")),
                    altura = cursor.getDouble(cursor.getColumnIndexOrThrow("altura")),
                    entrenado = cursor.getString(cursor.getColumnIndexOrThrow("entrenado")).toBoolean()
                )
                mascotas.add(mascota)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return mascotas
    }

    fun obtenerPorId(id: Int): Mascota? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Mascota WHERE id = ?", arrayOf(id.toString()))
        var mascota: Mascota? = null

        if (cursor.moveToFirst()) {
            mascota = Mascota(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                raza = cursor.getString(cursor.getColumnIndexOrThrow("raza")),
                sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo")).toBoolean(),
                peso = cursor.getDouble(cursor.getColumnIndexOrThrow("peso")),
                altura = cursor.getDouble(cursor.getColumnIndexOrThrow("altura")),
                entrenado = cursor.getString(cursor.getColumnIndexOrThrow("entrenado")).toBoolean()
            )
        }

        cursor.close()
        db.close()
        return mascota
    }
}