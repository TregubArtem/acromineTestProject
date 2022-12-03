package com.tregub.acromine.screen.data

/**
 * The final set of properties that describes acronym definition to the user
 *
 * @param name representative form of the full form
 * @param frequency the number of occurrences of the definition in the corpus
 * @param year the year when the definition appeared for the first time in the corpus
 * @param variationsCount how many different definitions are there
 */
data class AcronymDefinitionViewState(
    val name: String,
    val frequency: Int,
    val year: Int,
    val variationsCount: Int,
)
