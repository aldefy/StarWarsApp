package com.star.wars.search.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

data class SearchPeopleResponse(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("results")
    val results: List<CharacterResultItem>
)

@Parcelize
data class CharacterResultItem(
    @SerializedName("films")
    val films: List<String>? = null,
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
    val vehicles: List<String>? = null,
    @SerializedName("url")
    var url: HttpUrl = "",
    @SerializedName("hair_color")
    val hairColor: String = "",
    @SerializedName("birth_year")
    val birthYear: String = "",
    @SerializedName("eye_color")
    val eyeColor: String = "",
    @SerializedName("starships")
    val starships: List<String>? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("height")
    val height: String = ""
): Parcelable


