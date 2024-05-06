package com.example.examenparcial2mendozareyesangelemanuel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun showMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.overflow_menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.registrar -> {
                    startActivity(Intent(this, Registrar::class.java))
                    true
                }
                R.id.consultar -> {
                    startActivity(Intent(this, Consultar::class.java))
                    true
                }
                R.id.salir -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}
