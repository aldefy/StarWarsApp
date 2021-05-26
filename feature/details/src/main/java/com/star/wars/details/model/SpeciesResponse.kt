package com.star.wars.details.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpeciesResponse(
    @SerializedName("films")
    val films: List<HttpUrl>?,
    @SerializedName("skin_colors")
    val skinColors: String = "",
    @SerializedName("homeworld")
    val homeworld: String = "",
    @SerializedName("edited")
    val edited: String = "",
    @SerializedName("created")
    val created: String = "",
    @SerializedName("eye_colors")
    val eyeColors: String = "",
    @SerializedName("language")
    val language: String = "",
    @SerializedName("classification")
    val classification: String = "",
    @SerializedName("people")
    val people: List<HttpUrl>?,
    @SerializedName("url")
    val url: HttpUrl = "",
    @SerializedName("hair_colors")
    val hairColors: String = "",
    @SerializedName("average_height")
    val averageHeight: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("designation")
    val designation: String = "",
    @SerializedName("average_lifespan")
    val averageLifespan: String = ""
): Parcelable
