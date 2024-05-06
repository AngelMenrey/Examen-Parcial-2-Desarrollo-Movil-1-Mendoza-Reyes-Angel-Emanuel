package com.example.examenparcial2mendozareyesangelemanuel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinnerOpciones)
        val campoTexto = findViewById<EditText>(R.id.campoTexto)
        val botonIngresar = findViewById<Button>(R.id.botonIngresar)
        val botonSalir = findViewById<Button>(R.id.botonSalir)

        val nombresUsuarios = arrayOf("Armando", "Samuel", "Juan", "Pedro", "María", "José", "Luis", "Jorge", "Carlos", "Daniel")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresUsuarios)
        spinner.adapter = adapter

        botonIngresar.setOnClickListener {
            val contraseña = campoTexto.text.toString()

            if (contraseña == "12345") {
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
        }

        botonSalir.setOnClickListener {
            limpiarDatos()
            finishAffinity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        limpiarDatos()
    }

    private fun limpiarDatos() {
        val elementosSharedPreferences = getSharedPreferences("elementos", Context.MODE_PRIVATE)
        val editor = elementosSharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
