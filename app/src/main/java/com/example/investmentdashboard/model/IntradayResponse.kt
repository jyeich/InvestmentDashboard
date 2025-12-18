package com.example.investmentdashboard.model



data class IntradayResponse (
    val meta: StockMeta,
    val data: Map<String, List<StockPrice>>
)

data class StockMeta (
    val date_from: String,
    val date_to: String,
    val max_period_days: Int
)

data class StockPrice(
    val ticker: String,
    val data: StockCandle
    )


data class StockCandle (
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    val is_extended_hours: Boolean
)