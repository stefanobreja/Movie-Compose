package com.obi.moviecompose.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class ProductionCompany(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)