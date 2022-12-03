package com.tregub.acromine.screen.data

import com.tregub.acromine.feature.data.model.AcronymDefinition
import javax.inject.Inject

/**
 * A contract for converting data model to UI model
 *  for [AcronymDefinition] and [AcronymDefinitionViewState]
 */
interface AcronymDefinitionMapper {

    /**
     * @param src the actual instance to be converted
     * @return corresponding instance of [AcronymDefinitionViewState]
     */
    fun toViewState(src: AcronymDefinition): AcronymDefinitionViewState
}

/**
 * The actual implementation of [AcronymDefinitionMapper]
 */
class AcronymDefinitionMapperImpl @Inject constructor() : AcronymDefinitionMapper {

    override fun toViewState(src: AcronymDefinition): AcronymDefinitionViewState =
        AcronymDefinitionViewState(
            name = src.name,
            frequency = src.frequency,
            year = src.year,
            variationsCount = src.variations.size,
        )
}
