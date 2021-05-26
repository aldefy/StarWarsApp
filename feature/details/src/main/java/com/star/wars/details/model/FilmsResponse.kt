package com.star.wars.details.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmsResponse(
    @SerializedName("edited")
    val edited: String = "",
    @SerializedName("director")
    val director: String = "",
    @SerializedName("created")
    val created: String = "",
    @SerializedName("vehicles")
    val vehicles: List<HttpUrl>?,
    @SerializedName("opening_crawl")
    val openingCrawl: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: HttpUrl = "",
    @SerializedName("characters")
    val characters: List<HttpUrl>?,
    @SerializedName("episode_id")
    val episodeId: Int = 0,
    @SerializedName("planets")
    val planets: List<HttpUrl>?,
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("starships")
    val starships: List<HttpUrl>?,
    @SerializedName("species")
    val species: List<HttpUrl>?,
    @SerializedName("producer")
    val producer: String = ""
): Parcelable
