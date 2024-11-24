package com.example.calculadora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentNumber = ""
    private var operator: String? = null
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(
            R.id.btn_0 to "0", R.id.btn_1 to "1", R.id.btn_2 to "2", R.id.btn_3 to "3",
            R.id.btn_4 to "4", R.id.btn_5 to "5", R.id.btn_6 to "6", R.id.btn_7 to "7",
            R.id.btn_8 to "8", R.id.btn_9 to "9", R.id.btn_dot to ".",
            R.id.btn_add to "+", R.id.btn_subtract to "-", R.id.btn_multiply to "×", R.id.btn_divide to "÷",
            R.id.btn_equals to "=", R.id.btn_clear to "C"
        )

        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { onButtonClick(value) }
        }
    }

    private fun onButtonClick(value: String) {
        when (value) {
            "C" -> clear()
            "+", "-", "×", "÷" -> setOperator(value)
            "=" -> calculate()
            else -> appendNumber(value)
        }
    }

    private fun appendNumber(value: String) {
        if (value == "." && currentNumber.contains(".")) return
        currentNumber += value
        display.text = currentNumber
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toDouble()
            operator = op
            currentNumber = ""
        }
    }

    private fun calculate() {
        if (operator != null && firstOperand != null && currentNumber.isNotEmpty()) {
            val secondOperand = currentNumber.toDouble()
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "×" -> firstOperand!! * secondOperand
                "÷" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else "Error"
                else -> return
            }
            display.text = result.toString()
            clear()
        }
    }

    private fun clear() {
        currentNumber = ""
        firstOperand = null
        operator = null
        display.text = "0"
    }
}
