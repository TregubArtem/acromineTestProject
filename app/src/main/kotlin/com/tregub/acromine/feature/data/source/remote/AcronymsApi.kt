package com.tregub.acromine.feature.data.source.remote

import com.tregub.acromine.feature.data.model.AcronymWithDefinitions
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API declaration for finding full forms of acronyms
 */
interface AcronymsApi {

    private companion object {

        const val GATEWAY_ACRONYM_DEFINITIONS: String = "dictionary.py"

        const val KEY_SHORT_FORM: String = "sf"
    }

    /**
     * Provides abbreviation definitions
     *
     * @param acronym abbreviation for which definitions should be obtained
     * @return collection of abbreviations and related definitions
     */
    @GET(GATEWAY_ACRONYM_DEFINITIONS)
    suspend fun getDefinitions(
        @Query(KEY_SHORT_FORM)
        acronym: String,
    ): List<AcronymWithDefinitions>
}
