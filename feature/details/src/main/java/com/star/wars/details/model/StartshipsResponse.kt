package com.star.wars.details.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class StartshipsResponse(
    @SerializedName("max_atmosphering_speed")
    val maxAtmospheringSpeed: String = "",
    @SerializedName("cargo_capacity")
    val cargoCapacity: String = "",
    @SerializedName("films")
    val films: List<HttpUrl>?,
    @SerializedName("passengers")
    val passengers: String = "",
    @SerializedName("pilots")
    val pilots: List<HttpUrl>?,
    @SerializedName("edited")
    val edited: String = "",
    @SerializedName("consumables")
    val consumables: String = "",
    @SerializedName("MGLT")
    val mglt: String = "",
    @SerializedName("created")
    val created: String = "",
    @SerializedName("length")
    val length: String = "",
    @SerializedName("starship_class")
    val starshipClass: String = "",
    @SerializedName("url")
    val url: HttpUrl = "",
    @SerializedName("manufacturer")
    val manufacturer: String = "",
    @SerializedName("crew")
    val crew: String = "",
    @SerializedName("hyperdrive_rating")
    val hyperdriveRating: String = "",
    @SerializedName("cost_in_credits")
    val costInCredits: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("model")
    val model: String = ""
): Parcelable
