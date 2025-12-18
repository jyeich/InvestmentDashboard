package com.example.investmentdashboard.net

import com.example.investmentdashboard.model.StockResponse
import com.example.investmentdashboard.model.TimeSeriesResponse
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {
    @GET("query")
    suspend fun getStockPrice(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): StockResponse

    @GET("query")
    suspend fun getStockTimeSeries(
        @Query("function") function: String, // TIME_SERIES_INTRADAY, TIME_SERIES_DAILY, TIME_SERIES_WEEKLY
        @Query("symbol") symbol: String,
        @Query("interval") interval: String,
        @Query("apikey") apiKey: String
    ): ResponseBody
//    ): TimeSeriesResponse

    @GET("query")
    suspend fun getDailyTimeSeries(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ): TimeSeriesResponse
}
