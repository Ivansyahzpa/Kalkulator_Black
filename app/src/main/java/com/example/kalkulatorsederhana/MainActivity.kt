package com.example.kalkulatorsederhana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView

    // Variabel untuk menyimpan state kalkulator
    private var operand1: Double? = null
    private var pendingOperation = ""
    private var isNewNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
    }

    // Fungsi ini dipanggil saat tombol angka (0-9) ditekan
    fun onNumberClick(view: View) {
        if (isNewNumber) {
            resultTextView.text = ""
            isNewNumber = false
        }

        val button = view as Button
        var currentText = resultTextView.text.toString()

        // Mencegah "0" di awal jika sudah ada angka lain
        if (currentText == "0") {
            currentText = ""
        }

        resultTextView.text = currentText + button.text.toString()
    }

    // Fungsi ini dipanggil saat tombol operator (+, -, *, /) ditekan
    fun onOperatorClick(view: View) {
        val button = view as Button

        // Jika sudah ada operasi yang tertunda, hitung dulu
        if (operand1 != null && !isNewNumber) {
            onEqualsClick(view)
        }

        operand1 = resultTextView.text.toString().toDoubleOrNull()
        pendingOperation = button.text.toString()
        isNewNumber = true
    }

    // Fungsi ini dipanggil saat tombol sama dengan (=) ditekan
    fun onEqualsClick(view: View) {
        if (operand1 == null || isNewNumber) return

        val operand2 = resultTextView.text.toString().toDoubleOrNull()
        if (operand2 == null) return

        val result = when (pendingOperation) {
            "+" -> operand1!! + operand2
            "-" -> operand1!! - operand2
            "*" -> operand1!! * operand2
            "/" -> {
                if (operand2 == 0.0) {
                    "Error" // Mencegah pembagian dengan nol
                } else {
                    operand1!! / operand2
                }
            }
            else -> resultTextView.text.toString()
        }

        resultTextView.text = result.toString()
        resetCalculatorState()
        isNewNumber = true // Siap untuk input baru setelah hasil muncul
    }

    // Fungsi ini dipanggil saat tombol Clear (C) ditekan
    fun onClearClick(view: View) {
        resultTextView.text = "0"
        resetCalculatorState()
    }

    // Fungsi untuk mereset state kalkulator
    private fun resetCalculatorState() {
        operand1 = null
        pendingOperation = ""
        isNewNumber = true
    }
}