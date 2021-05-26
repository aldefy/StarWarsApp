package com.star.wars.details.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanetsResponse(
    @SerializedName("films")
    val films: List<HttpUrl>?,
    @SerializedName("edited")
    val edited: String = "",
    @SerializedName("created")
    val created: String = "",
    @SerializedName("climate")
    val climate: String = "",
    @SerializedName("rotation_period")
    val rotationPeriod: String = "",
    @SerializedName("url")
    val url: HttpUrl = "",
    @SerializedName("population")
    val population: String = "",
    @SerializedName("orbital_period")
    val orbitalPeriod: String = "",
    @SerializedName("surface_water")
    val surfaceWater: String = "",
    @SerializedName("diameter")
    val diameter: String = "",
    @SerializedName("gravity")
    val gravity: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("residents")
    val residents: List<HttpUrl>?,
    @SerializedName("terrain")
    val terrain: String = ""
): Parcelable
