package com.example.deber_equipobasket.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDeDatos(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "EquiposBasketDB.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_EQUIPO = "Equipo"
        private const val TABLE_JUGADOR = "Jugador"

        private const val CREATE_TABLE_EQUIPO = """
            CREATE TABLE $TABLE_EQUIPO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                ciudad TEXT NOT NULL,
                fundado TEXT NOT NULL,
                campeonatosGanados INTEGER NOT NULL,
                activo TEXT NOT NULL,
                promedioPuntos REAL NOT NULL
            );
        """

        private const val CREATE_TABLE_JUGADOR = """
            CREATE TABLE $TABLE_JUGADOR (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_equipo INTEGER NOT NULL,
                nombre TEXT NOT NULL,
                edad INTEGER NOT NULL,
                estatura REAL NOT NULL,
                fechaIngreso TEXT NOT NULL,
                esTitular TEXT NOT NULL,
                FOREIGN KEY(id_equipo) REFERENCES $TABLE_EQUIPO(id) ON DELETE CASCADE
            );
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_EQUIPO)
        db?.execSQL(CREATE_TABLE_JUGADOR)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_JUGADOR")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_EQUIPO")
            onCreate(db)
        }
    }
}