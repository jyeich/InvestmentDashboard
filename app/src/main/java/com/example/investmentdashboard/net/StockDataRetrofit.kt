package com.example.investmentdashboard.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StockDataRetrofit {

    private const val BASE_URL = "https://api.stockdata.org/v1/"

    val api: StockDataApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockDataApi::class.java)
    }
}