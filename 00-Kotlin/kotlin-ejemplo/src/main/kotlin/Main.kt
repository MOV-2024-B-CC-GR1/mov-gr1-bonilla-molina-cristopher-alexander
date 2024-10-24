package org.example
import java.util.*

fun main(args: Array<String>){

    //INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String ="Adrian";
    //inmutable = "Vicente"; //Error!

    //MUTABLES
    var mutable: String = "Vicente";
    mutable = "Adrian"; //OK
    // VAL > VAR

    //Duck Typing
    val ejemploVariable = "Cristopher Bonilla"
    ejemploVariable.trim()
    val edadEjemplo: Int = 12
    //ejemploVariable = edadEjemplo // Error!

    //Variables Primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases en JAVA
    val fechaNacimiento: Date = Date()

    //When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen = "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito
    imprimirNombre("Cristopher")
}

fun imprimirNombre(nombre: String): Unit{
    fun otraFuncionAdentro(){
        println("Otra función dentro")
    }
    println("Nombre: $nombre") //Uson sin llaves
    println("Nombre: ${nombre}") //Uso con llaves opcional
    println("Nombre: ${nombre + nombre}") // Uso con llaves (concatenado)
    println("Nombre: ${nombre.toString()}") // Uso con llaves (función)
    println("Nombre: $nombre.toString()") //INCORRECTO! no se puede usar sin llaves
    otraFuncionAdentro()
}