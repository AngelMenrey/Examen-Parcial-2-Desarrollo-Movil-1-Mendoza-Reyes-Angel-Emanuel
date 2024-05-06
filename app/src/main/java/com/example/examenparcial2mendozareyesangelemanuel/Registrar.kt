package com.example.examenparcial2mendozareyesangelemanuel

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Intent

class Registrar : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var codigoEditText: EditText
    private lateinit var activoCheckBox: CheckBox
    private lateinit var precioEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var actualizarButton: Button
    private lateinit var buscarButton: Button
    private lateinit var limpiarButton: Button
    private lateinit var botonRegresar: Button

    private lateinit var elementosSharedPreferences: SharedPreferences
    private lateinit var elementosEditor: SharedPreferences.Editor
    private var elementos: MutableList<Elemento> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        nombreEditText = findViewById(R.id.editTextNombre)
        descripcionEditText = findViewById(R.id.editTextDescripcion)
        codigoEditText = findViewById(R.id.editTextCodigo)
        activoCheckBox = findViewById(R.id.checkBoxActivo)
        precioEditText = findViewById(R.id.editTextPrecio)
        registrarButton = findViewById(R.id.botonRegistrar)
        actualizarButton = findViewById(R.id.botonActualizar)
        buscarButton = findViewById(R.id.botonBuscar)
        limpiarButton = findViewById(R.id.botonLimpiar)
        botonRegresar = findViewById(R.id.botonRegresar)

        elementosSharedPreferences = getSharedPreferences("elementos", Context.MODE_PRIVATE)
        elementosEditor = elementosSharedPreferences.edit()

        cargarElementosGuardados()

        registrarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()
            val codigo = codigoEditText.text.toString()
            val activo = activoCheckBox.isChecked
            val precio = precioEditText.text.toString()

            if (nombre.isNotBlank() && descripcion.isNotBlank() && codigo.isNotBlank() && precio.isNotBlank()) {
                val nuevoElemento = Elemento(nombre, descripcion, codigo, activo, precio)
                elementos.add(nuevoElemento)
                guardarElementos()
                Toast.makeText(this, "Se agregó correctamente la información", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        buscarButton.setOnClickListener {
            val codigoBusqueda = codigoEditText.text.toString()
            if (codigoBusqueda.isNotBlank()) {
                val elementoEncontrado = elementos.find { it.codigo == codigoBusqueda }
                if (elementoEncontrado != null) {
                    nombreEditText.setText(elementoEncontrado.nombre)
                    descripcionEditText.setText(elementoEncontrado.descripcion)
                    activoCheckBox.isChecked = elementoEncontrado.activo
                    precioEditText.setText(elementoEncontrado.precio)
                    Toast.makeText(this, "Se encontró la información", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No se encontró ningún elemento con ese código", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa un código para realizar la búsqueda", Toast.LENGTH_SHORT).show()
            }
        }

        actualizarButton.setOnClickListener {
            val codigo = codigoEditText.text.toString()
            val elementoExistente = elementos.find { it.codigo == codigo }
            if (elementoExistente != null) {
                val nombre = nombreEditText.text.toString()
                val descripcion = descripcionEditText.text.toString()
                val activo = activoCheckBox.isChecked
                val precio = precioEditText.text.toString()

                elementoExistente.apply {
                    this.nombre = nombre
                    this.descripcion = descripcion
                    this.activo = activo
                    this.precio = precio
                }
                guardarElementos()
                Toast.makeText(this, "Se actualizó correctamente la información", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se encontró ningún elemento con ese código", Toast.LENGTH_SHORT).show()
            }
        }

        limpiarButton.setOnClickListener {
            nombreEditText.text.clear()
            descripcionEditText.text.clear()
            codigoEditText.text.clear()
            activoCheckBox.isChecked = false
            precioEditText.text.clear()
            Toast.makeText(this, "Se limpiaron todos los campos", Toast.LENGTH_SHORT).show()
        }

        botonRegresar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }


    private fun limpiarCampos() {
        nombreEditText.text.clear()
        descripcionEditText.text.clear()
        codigoEditText.text.clear()
        activoCheckBox.isChecked = false
        precioEditText.text.clear()
    }

    private fun cargarElementosGuardados() {
        val json = elementosSharedPreferences.getString("elementos_lista", null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Elemento>>() {}.type
        elementos = gson.fromJson(json, type) ?: mutableListOf()
    }

    private fun guardarElementos() {
        val gson = Gson()
        val json = gson.toJson(elementos)
        elementosEditor.putString("elementos_lista", json)
        elementosEditor.apply()
    }

}
