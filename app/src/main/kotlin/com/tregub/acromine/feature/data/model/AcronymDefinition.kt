package com.tregub.acromine.feature.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Definition (long / full form) of the abbreviation with possible variations
 *
 * @param name representative form of the full form
 * @param frequency the number of occurrences of the definition in the corpus
 * @param year the year when the definition appeared for the first time in the corpus
 * @param variations a collection of variation objects
 *  that gather surface expressions of the full form in the corpus
 */
/*
{
  "lf": "collagen-induced arthritis",
  "freq": 868,
  "since": 1983,
  "vars": []
}
 */
@JsonClass(generateAdapter = true)
class AcronymDefinition(
    @Json(name = "lf")
    val name: String,
    @Json(name = "freq")
    val frequency: Int,
    @Json(name = "since")
    val year: Int,
    @Json(name = "vars")
    val variations: List<AcronymDefinition> = emptyList(),
)
