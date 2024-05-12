package com.example.app

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private var shouldRecreate = false // Bandera para controlar la recreación de la actividad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.country_list_spinner)

        // Obtén el array de países de los recursos
        val countriesArray = resources.getStringArray(R.array.country_spinner_items)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countriesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = countriesArray[position]

                // Si el idioma no necesita cambiar, no recreamos la actividad
                when (selectedCountry) {
                    getString(R.string.country_0) -> setLocale("en") // EUA
                    getString(R.string.country_1) -> setLocale("es-rMX") // México
                    getString(R.string.country_2) -> setLocale("pt") // Brasil
                    getString(R.string.country_3) -> setLocale("fr") // Francia
                    getString(R.string.country_4) -> setLocale("de") // Alemania
                    getString(R.string.country_5) -> setLocale("it") // Italia
                    // Agrega más casos según sea necesario para otros países
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el caso en el que no se selecciona ningún elemento.
            }
        }

        val submitButton: Button = findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            val message = getString(R.string.welcome)
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }
                .show()
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        if (locale != Locale.getDefault()) { // Verifica si el idioma ya está establecido
            shouldRecreate = true // Necesitamos recrear la actividad solo si el idioma cambia
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        }

        if (shouldRecreate) {
            recreate() // Si es necesario, recrea la actividad después de cambiar el idioma
        }
    }
}