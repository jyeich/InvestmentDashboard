package com.example.investmentdashboard.model

import com.google.gson.annotations.SerializedName

data class TimeSeriesResponse(

    @SerializedName("Meta Data")
    val metaData: MetaData,

    @SerializedName("Time Series (Daily)")
    val timeSeriesResponse: Map<String, Candle>,

    @SerializedName("Time Series (5min)")
    val timeSeries5min: Map<String, Candle>,

    @SerializedName("Time Series (15min)")
    val timeSeries15min: Map<String, Candle>,
)
