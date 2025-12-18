package com.example.investmentdashboard

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.investmentdashboard.adapter.StockAdapter
import com.example.investmentdashboard.model.Candle
import com.example.investmentdashboard.model.StockCandle
import com.example.investmentdashboard.model.StockChartData
import com.example.investmentdashboard.net.AlphaRetrofitInstance
import com.example.investmentdashboard.net.StockDataRetrofit
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var symbolEditText: EditText
    private lateinit var fetchButton: Button
    private lateinit var timeSeriesButton: Button
    private lateinit var stockIntradayButton: Button
    private lateinit var resultTextView: TextView
    private val TAG: String = "InvestmentDashboard"
    val stocklist = listOf("AAPL", "MSTR", "NVDA")
    val stockDataMap = mutableMapOf<String, List<StockCandle>>()
    val stockChartList = stockDataMap.map { (symbol, candles) ->
        StockChartData(symbol, candles)
    }
    private lateinit var stockAdapter: StockAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        symbolEditText = findViewById(R.id.symbolEditText)
        fetchButton = findViewById(R.id.fetchButton)
        timeSeriesButton = findViewById(R.id.timeSeriesButton)
        stockIntradayButton = findViewById(R.id.stockIntradayButton)
        resultTextView = findViewById(R.id.resultTextView)

        fetchButton.setOnClickListener {
            val symbol = symbolEditText.text.toString()
            fetchStockPrice(symbol)
        }

        timeSeriesButton.setOnClickListener{
            val symbol = symbolEditText.text.toString()
            fetchTimeSeries(symbol)
        }

        stockIntradayButton.setOnClickListener {
            val symbol = symbolEditText.text.toString()
            fetchStockDataIntraDAY()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        stockAdapter = StockAdapter(mutableListOf()) // start empty
        recyclerView.adapter = stockAdapter

    }

    private fun fetchStockPrice(symbol: String) {
        lifecycleScope.launch {
            try {
                val response = AlphaRetrofitInstance.api.getStockPrice(symbol = symbol, apiKey = "CF0C0Y5BJT8EZAH9")
                val quote = response.globalQuote
                resultTextView.text = "${quote.symbol}: ${quote.price} (${quote.changePercent})"
            } catch (e: Exception) {
                resultTextView.text = "Error: ${e.message}"
            }
        }
    }

    private fun fetchTimeSeries(symbol: String) {
        lifecycleScope.launch {
            try {
                val response = AlphaRetrofitInstance.api.getDailyTimeSeries(
                    symbol = symbol,
                    apiKey = "CF0C0Y5BJT8EZAH9"
                )
                Log.d(TAG, response.toString())

                for((timestamp, candle) in response.timeSeriesResponse) {
                    Log.d(TAG, ("$timestamp -> close=${candle.close}"))
                }
                val candles: Map<String, Candle> =
                response.timeSeriesResponse.toSortedMap(compareByDescending { it })

                Log.d(TAG, candles.entries.first().toString())

            } catch (e: Exception) {
                Log.d(TAG, "error fetching time series from alpha vanatage: $e")
            }
        }
    }

    private fun fetchStockDataIntraDAY() {
        lifecycleScope.launch {
            try {

                stocklist.forEach {symbol ->
                    val response = StockDataRetrofit.api.getIntraday(
                        symbols = symbol,
                        apiToken = "LdQGn49YaIDbz5Z9xbv8FsKEdsWzIu9tQotgg26j",
                        interval = "minute",
                        sort = "dess",
                        keyByDate = true
                    )
                    val candles = response.data.values
                        .flatMap { it }
                        .map {it.data}
                        .toList()
                    stockDataMap[symbol] = candles
                }
                val stockChartList = stockDataMap.map { (symbol, candles) ->
                    StockChartData(symbol, candles)
                }
                stockAdapter.updateData(stockChartList)
                // Likely need to update adapter here

            } catch (e: Exception) {
                Log.e(TAG, "Intraday fetch error", e)
            }
        }
    }
}


