package com.example.investmentdashboard.model

data class StockItem(
    val symbol: String,
    val candles: List<StockCandle>
)
data class StockChartData(
    val ticker: String,
    val candles: List<StockCandle>
)