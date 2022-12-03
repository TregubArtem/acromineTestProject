package com.tregub.acromine.feature.data

import com.tregub.acromine.feature.data.model.AcronymDefinition
import com.tregub.acromine.feature.data.model.AcronymWithDefinitions
import com.tregub.acromine.feature.data.source.local.AcronymsCache
import com.tregub.acromine.feature.data.source.remote.AcronymsApi
import com.tregub.acromine.feature.dependency.DiConstant
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * A contract for a data source that works with abbreviations and related definitions
 */
interface AcronymsRepository {

    /**
     * Provides acronym definitions
     *
     * @param acronym abbreviation for which definitions should be provided
     * @return collection of definitions related to provided [acronym]
     */
    suspend fun getDefinitions(acronym: String): List<AcronymDefinition>
}

/**
 * The actual implementation of [AcronymsRepository]
 *
 * @param nonUiContext where the initial calculations should be done
 * @param ioContext where data from a remote source should be processed
 * @param api provides data from a remote source
 * @param cache provides data from a local source
 */
class AcronymsRepositoryImpl @Inject constructor(
    @DiConstant.CoroutineContextNonUI
    private val nonUiContext: CoroutineContext,
    @DiConstant.CoroutineContextIO
    private val ioContext: CoroutineContext,
    private val api: AcronymsApi,
    private val cache: AcronymsCache,
) : AcronymsRepository {

    override suspend fun getDefinitions(acronym: String): List<AcronymDefinition> {
        var result: List<AcronymDefinition> = withContext(nonUiContext) { cache[acronym] }
        if (result.isNotEmpty()) {
            Timber.v("getDefinitions from local: $acronym - ${result.size}")
            return result
        }
        withContext(ioContext) {
            val acronymsWithDefinitions: List<AcronymWithDefinitions> = api.getDefinitions(acronym)
            acronymsWithDefinitions.forEach { acronymWithDefinitions ->
                cache[acronymWithDefinitions.name] = acronymWithDefinitions.definitions
            }
            result = cache[acronym]
        }
        Timber.v("getDefinitions from remote: $acronym - ${result.size}")
        return result
    }
}
