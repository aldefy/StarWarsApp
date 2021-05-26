package com.star.wars.details.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDetailsResponse(
    @SerializedName("films")
    var films: List<HttpUrl>?,
    @SerializedName("homeworld")
    val homeworld: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("skin_color")
    val skinColor: String = "",
    @SerializedName("edited")
    val edited: String = "",
    @SerializedName("created")
    val created: String = "",
    @SerializedName("mass")
    val mass: String = "",
    @SerializedName("vehicles")
    var vehicles: List<HttpUrl>?,
    @SerializedName("species")
    val species: List<HttpUrl>?,
    @SerializedName("url")
    val url: HttpUrl = "",
    @SerializedName("hair_color")
    val hairColor: String = "",
    @SerializedName("birth_year")
    val birthYear: String = "",
    @SerializedName("eye_color")
    val eyeColor: String = "",
    @SerializedName("starships")
    var starships: List<HttpUrl>?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("height")
    val height: String = ""
) : Parcelable


