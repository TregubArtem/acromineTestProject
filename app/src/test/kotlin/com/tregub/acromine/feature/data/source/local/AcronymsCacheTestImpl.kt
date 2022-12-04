package com.tregub.acromine.feature.data.source.local

import com.tregub.acromine.feature.data.model.AcronymDefinition

/**
 * A test implementation of [AcronymsCache] which is a wrapper for [MutableMap]
 */
class AcronymsCacheTestImpl : AcronymsCache {

    private val map: MutableMap<String, List<AcronymDefinition>> = mutableMapOf()

    override fun get(acronym: String): List<AcronymDefinition> =
        map[acronym] ?: emptyList()

    override fun set(acronym: String, definitions: List<AcronymDefinition>) {
        map[acronym] = definitions
    }
}
