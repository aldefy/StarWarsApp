package com.star.wars.details.model

data class DetailsFilmsCombinedResult(
    val films: List<FilmsResponse>
)

data class DetailsSpeciesCombinedResult(
    val species: List<SpeciesResponse>
)
