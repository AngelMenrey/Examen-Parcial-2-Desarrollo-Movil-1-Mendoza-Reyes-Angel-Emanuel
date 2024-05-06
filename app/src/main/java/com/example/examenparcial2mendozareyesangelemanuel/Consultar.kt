package com.example.examenparcial2mendozareyesangelemanuel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Consultar : AppCompatActivity() {
    private lateinit var textViewMostrar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar)

        textViewMostrar = findViewById(R.id.textViewMostrar)

        if (!isElementosVacios()) {
            cargarElementosGuardados()
        }
    }

    private fun cargarElementosGuardados() {
        val elementosSharedPreferences = getSharedPreferences("elementos", MODE_PRIVATE)
        val json = elementosSharedPreferences.getString("elementos_lista", null)

        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Elemento>>() {}.type
            val elementos = gson.fromJson<MutableList<Elemento>>(json, type)

            val textoMostrar = StringBuilder()
            for (elemento in elementos) {
                textoMostrar.append("Nombre: ${elemento.nombre}\n")
                textoMostrar.append("Descripción: ${elemento.descripcion}\n")
                textoMostrar.append("Código: ${elemento.codigo}\n")
                textoMostrar.append("Activo: ${if (elemento.activo) "Sí" else "No"}\n")
                textoMostrar.append("Precio: ${elemento.precio}\n\n")
            }

            textViewMostrar.text = textoMostrar.toString()
        }
    }

    private fun isElementosVacios(): Boolean {
        val elementosSharedPreferences = getSharedPreferences("elementos", MODE_PRIVATE)
        val json = elementosSharedPreferences.getString("elementos_lista", null)
        return json.isNullOrEmpty()
    }
}
