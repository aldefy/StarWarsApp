package com.star.wars.details.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDetailsMeta(
    val characterName: String,
    val urls: List<String>
) : Parcelable
