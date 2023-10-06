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
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun onDigitClick(view : View) {
        if(stateError){
            binding.dataTv.text = (view as Button).text
                stateError = false

        }
        else{
            binding.dataTv.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }


    fun onOperatorClick() {}


    fun onAllClearClick() {}


    fun onEqualClick() {}


    fun onBackClick() {}


    fun onClearClick() {}

    @SuppressLint("SetTextI18n")
    fun onEqual(){
        if(lastNumeric &&  !stateError){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try{

                val result = expression.evaluate()
                binding.resultTv.visibility  = View.VISIBLE
                binding.resultTv.text =  "=" + result.toString()

            }catch (ex : ArithmeticException){
                Log.e("Evaluate error " , ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}