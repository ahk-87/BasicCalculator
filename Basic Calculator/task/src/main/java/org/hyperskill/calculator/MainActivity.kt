package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    var secondTerm = 0.0
    var op = "new"
    var lastOp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.displayEditText)
        editText.inputType = InputType.TYPE_NULL

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            editText.text.clear()
            editText.hint = "0"
            op = "new"
            secondTerm = 0.0
        }
        findViewById<Button>(R.id.equalButton).setOnClickListener { calculate(true) }
    }

    fun interceptClick(v: View) {
        when ((v as Button).text) {
            "." -> {
                if (editText.text.isEmpty() || editText.text.contentEquals("-"))
                    editText.text.append("0.")
                else if (!editText.text.contains('.')) editText.text.append('.')
            }

            else -> {
                if (editText.text.contentEquals("0")) editText.text.clear()
                else if (editText.text.contentEquals("-0")) editText.setText("-")
                editText.text.append(v.text)
            }
        }
        lastOp = op
    }

    fun performOperation(v: View) {
        if ((v as Button).text.toString() == "-") {
            if (editText.text.isEmpty() && op.isNotEmpty()) {
                editText.text.append('-')
                return
            }
            if (editText.text.contentEquals("-")) return
        }

        if (editText.text.isNotEmpty() && !editText.text.contentEquals("-"))
            calculate(false)
        else
            secondTerm = editText.hint.toString().toDouble()

        op = v.text.toString()
        lastOp = op
    }

    fun calculate(fromEqual: Boolean) {
        val firstTerm = editText.hint.toString().toDouble()
        secondTerm = editText.text.toString().toDoubleOrNull() ?: secondTerm
        val result = when (lastOp) {
            "+" -> firstTerm + secondTerm
            "-" -> firstTerm - secondTerm
            "*" -> firstTerm * secondTerm
            "/" -> firstTerm / secondTerm
            else -> secondTerm
        }
        editText.text.clear()
        editText.hint = if (result.toString().endsWith(".0"))
            result.toString().dropLast(2) else result.toString()
        if (fromEqual) op = ""
    }
}