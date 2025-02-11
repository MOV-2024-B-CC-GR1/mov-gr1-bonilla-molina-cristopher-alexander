package com.example.proyecto.basedatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "PetUniverse.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_MASCOTA = "Mascota"

        private const val CREATE_TABLE_MASCOTA = """
            CREATE TABLE $TABLE_MASCOTA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                edad INTEGER NOT NULL,
                raza TEXT NOT NULL,
                sexo INTEGER NOT NULL,
                peso REAL NOT NULL,
                altura REAL NOT NULL,
                entrenado INTEGER NOT NULL
            );
        """

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_MASCOTA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_MASCOTA")
            onCreate(db)
        }
    }
}