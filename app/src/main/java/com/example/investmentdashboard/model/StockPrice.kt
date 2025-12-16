package com.example.investmentdashboard.model

import com.google.gson.annotations.SerializedName
// model/StockPrice.kt
data class GlobalQuote(
    @SerializedName("01. symbol") val symbol: String,
    @SerializedName("05. price") val price: String,
    @SerializedName("10. change percent") val changePercent: String
)

data class StockResponse(
    @SerializedName("Global Quote") val globalQuote: GlobalQuote
)
