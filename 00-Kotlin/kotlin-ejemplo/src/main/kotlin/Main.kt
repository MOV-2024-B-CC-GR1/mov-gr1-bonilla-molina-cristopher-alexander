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
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito

    imprimirNombre("Cristopher")

    calcularSueldo(10.00) //solo parametro requerido
    calcularSueldo(10.00, 15.00, 20.00) // parametro requerido y sobreescribir parametros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00) // usan el parametro bonoEspecial en 2da posición
                                                        // gracias a los parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    // usando el parametro bonoEspecial en 1ra posición
    // usando el parametro sueldo en 2da posición
    // usando el parametro tasa en 3era posición
    // gracias a los parámetros nombrados
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


/*Clase 25-10-2024*/
fun calcularSueldo(
    sueldo:Double, //Requerido
    tasa:Double = 12.00, // Opcional (defecto)
    bonoEspecial:Double? = null //Opcional (nullable)
    // Variable? -> "?" Es Nullable (En algún momento puede ser nulo)
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( //Constructor primario
    // Caso 1) Parametro normal
    // uno: Int, (parametro sin modificador acceso)

    // Caso 2) Parametro y propiedad (atributo) (protected)
    // private var una: Int (propiedad "Instancia.uno")

    protected val numeroUno: Int, // instacncia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    parametroNoUsadoNoPropiedadDeLaClase:Int? = null
){
    init { // bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma( // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
): Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
){

    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicite: String = "Publico implicito"
    init { // bloque constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL  [propiedades, metodos]
        numeroDos // this. OPCIONAL  [propiedades, metodos]
        this.soyPublicoImplicite
        soyPublicoExplicito
    }
    constructor( // Constructor secundario
        uno: Int?, // Entero nullable
        dos: Int,
    ):this(
        if(uno == null) 0 else uno,
        dos
    ){
        // Bloque de codigo de constructor secundario
    }
    constructor( // Constructor secundario
        uno: Int,
        dos: Int?, // Entero nullable
    ):this(
        uno,
        if(dos == null) 0 else dos
    )
    constructor( // Constructor secundario
        uno: Int?, // Entero nullable
        dos: Int?,  // Entero nullable
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )
    fun sumar ():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object { // Comparte entre todas las instancias, similar al STATIC
        // funciones, variables
        // NombreClase.metodo, NombreClase.funcion =>
        // Suma.pi
        val pi = 3.14
        // Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num * num }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){ // Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }
}
