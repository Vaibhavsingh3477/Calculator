package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastNumeric = false
    private var stateError = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.dataTv.text = (view as Button).text
            stateError = false
        } else {
            binding.dataTv.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastNumeric) {
            binding.dataTv.append((view as Button).text)
            lastNumeric = false
            onEqual()
        }
    }

    fun onAllClearClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
        lastNumeric = false
        stateError = false
        binding.resultTv.visibility = View.GONE
    }

    fun onEqualClick(view: View) {
        onEqual()
    }

    fun onBackClick(view: View) {
        val text = binding.dataTv.text.toString()
        if (text.isNotEmpty()) {
            binding.dataTv.text = text.dropLast(1)
            if (text.last().isDigit()) {
                onEqual()
            }
        }
    }

    fun onClearClick(view: View) {
        binding.dataTv.text = ""
        lastNumeric = false
    }

    @SuppressLint("SetTextI18n")
    fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = binding.dataTv.text.toString()
            try {
                val result = ExpressionBuilder(txt).build().evaluate()
                binding.resultTv.apply {
                    visibility = View.VISIBLE
                    text = "=$result"
                }
            } catch (ex: ArithmeticException) {
                Log.e("Evaluate error", ex.toString())
                binding.resultTv.apply {
                    visibility = View.VISIBLE
                    text = "Error"
                }
                stateError = true
                lastNumeric = false
            }
        }
    }

}
