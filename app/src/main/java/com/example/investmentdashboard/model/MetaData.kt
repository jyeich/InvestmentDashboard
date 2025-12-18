package com.example.investmentdashboard.model

import com.google.gson.annotations.SerializedName

data class MetaData(

    @SerializedName("2. Symbol")
    val symbol: String,

    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String,

    @SerializedName("4. Interval")
    val interval: String,

    @SerializedName("6. Time Zone")
    val timeZone: String
)