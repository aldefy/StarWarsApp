package com.star.wars.common.data

import android.os.Parcelable
import com.star.wars.common.HttpUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDetailsMeta(
    val characterName: String,
    val url: HttpUrl
) : Parcelable
