package com.leonardogabrielmg.sorteiomegasena

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //referencia objetos do xml
    private lateinit var editReceivedValue: EditText
    private lateinit var textResult: TextView
    private lateinit var btnGenerate: LinearLayout
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //busca os objetos do xml
        editReceivedValue = findViewById(R.id.edit_input_value)
        textResult = findViewById(R.id.text_result)
        btnGenerate = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        result.let {
            textResult.text = "Ultimo sorteio: $it"
        }

        btnGenerate.setOnClickListener {
            if (!validate()) {
                Toast.makeText(
                    this,
                    "O campo deve ser preenchido com um valor entre 6 e 15.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            numbergenerator(editReceivedValue.text.toString(), textResult)
        }

    }

    private fun validate(): Boolean {
        return (editReceivedValue.text.toString().isNotEmpty()
                && editReceivedValue.text.toString().toInt() >= 6
                && editReceivedValue.text.toString().toInt() <= 15)
    }

    private fun numbergenerator(editReceivedValue: String, textResult: TextView) {
        //converte o valor recebido para inteiro
        val quant = editReceivedValue.toInt()
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            //gera números aleatórios de 0 a 59
            val number = random.nextInt(60)
            numbers.add(number + 1)

            //valida para retornar a quantidade de números selecionada
            if (numbers.size == quant) {
                break
            }

            textResult.text = numbers.joinToString(" - ")

            val edit = prefs.edit()
            edit.putString("result", textResult.text.toString())
            edit.apply()
        }
    }
}