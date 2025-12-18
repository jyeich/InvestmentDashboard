// StockDataApi.kt
package com.example.investmentdashboard.net

import com.example.investmentdashboard.model.IntradayResponse
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface StockDataApi {

    @GET("data/intraday")
    suspend fun getIntraday(
        @Query("symbols") symbols: String,
        @Query("api_token") apiToken: String,
        @Query("interval") interval: String? = null,
        @Query("sort") sort: String? = null,
        @Query("date_from") dateFrom: String? = null,
        @Query("date_to") dateTo: String? = null,
        @Query("key_by_date") keyByDate: Boolean? = null
    ): IntradayResponse
}
