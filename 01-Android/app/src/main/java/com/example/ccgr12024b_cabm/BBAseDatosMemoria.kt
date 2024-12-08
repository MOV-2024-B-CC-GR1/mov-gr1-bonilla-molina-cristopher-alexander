package com.example.ccgr12024b_cabm

class BBAseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "Cristopher", "a@a.com"))
            arregloBEntrenador.add(BEntrenador(2, "Benjamin", "b@b.com"))
            arregloBEntrenador.add(BEntrenador(3, "Renata", "c@c.com"))
        }
    }
}