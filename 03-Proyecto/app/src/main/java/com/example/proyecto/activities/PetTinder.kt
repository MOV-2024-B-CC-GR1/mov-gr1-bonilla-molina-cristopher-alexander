package com.example.proyecto.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.R

class PetTinder : AppCompatActivity() {

    private lateinit var imageViewPet: ImageView
    private lateinit var textViewPetName: TextView
    private lateinit var buttonLike: Button
    private lateinit var buttonDislike: Button

    // Lista de imágenes de mascotas (puedes usar recursos de drawable)
    private val petImages = listOf(
        R.drawable.pet1, // Reemplaza con tus imágenes
        R.drawable.pet2,
        R.drawable.pet3,
        R.drawable.pet4
    )

    // Lista de nombres de mascotas
    private val petNames = listOf(
        "Buddy",
        "Max",
        "Milo",
        "Lucy"
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_tinder)

        imageViewPet = findViewById(R.id.imageViewPet)
        textViewPetName = findViewById(R.id.textViewPetName)
        buttonLike = findViewById(R.id.buttonLike)
        buttonDislike = findViewById(R.id.buttonDislike)

        // Cargar la primera imagen y nombre
        loadPetImage()

        // Configurar los botones
        buttonLike.setOnClickListener {
            // Aquí puedes manejar la lógica de "like"
            currentIndex++
            if (currentIndex < petImages.size) {
                loadPetImage()
            } else {
                // Reiniciar el índice si se han mostrado todas las imágenes
                currentIndex = 0
                loadPetImage()
            }
        }

        buttonDislike.setOnClickListener {
            // Aquí puedes manejar la lógica de "dislike"
            currentIndex++
            if (currentIndex < petImages.size) {
                loadPetImage()
            } else {
                // Reiniciar el índice si se han mostrado todas las imágenes
                currentIndex = 0
                loadPetImage()
            }
        }
    }

    private fun loadPetImage() {
        imageViewPet.setImageResource(petImages[currentIndex])
        textViewPetName.text = petNames[currentIndex] // Mostrar el nombre de la mascota
    }
}