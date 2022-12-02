package com.tregub.acromine.feature.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Acronym (short form / abbreviation) with related definitions (long / full forms)
 *
 * @param name the abbreviation
 * @param definitions a collection of long forms associated with the short form
 */
/*
{
  "sf": "CIA",
  "lfs": []
}
 */
@JsonClass(generateAdapter = true)
class AcronymWithDefinitions(
    @Json(name = "sf")
    val name: String,
    @Json(name = "lfs")
    val definitions: List<AcronymDefinition>,
)
