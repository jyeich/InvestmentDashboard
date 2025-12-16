package com.example.investmentdashboard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.investmentdashboard.net.RetrofitInstance
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var symbolEditText: EditText
    private lateinit var fetchButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        symbolEditText = findViewById(R.id.symbolEditText)
        fetchButton = findViewById(R.id.fetchButton)
        resultTextView = findViewById(R.id.resultTextView)

        fetchButton.setOnClickListener {
            val symbol = symbolEditText.text.toString()
            fetchStockPrice(symbol)
        }
    }

    private fun fetchStockPrice(symbol: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getStockPrice(symbol = symbol, apiKey = "YOUR_KEY")
                val quote = response.globalQuote
                resultTextView.text = "${quote.symbol}: ${quote.price} (${quote.changePercent})"
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            }
        }
    }
}


