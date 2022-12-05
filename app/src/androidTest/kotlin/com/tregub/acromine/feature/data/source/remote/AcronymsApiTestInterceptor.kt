package com.tregub.acromine.feature.data.source.remote

import com.tregub.acromine.feature.data.model.AcronymDefinition
import com.tregub.acromine.feature.data.model.AcronymWithDefinitions
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Test implementation of [Interceptor] that provides different result for different acronyms:
 * * [AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME] -
 *  one instance of [AcronymWithDefinitions] which includes exactly one definition
 * * [AcronymsApiTestInterceptor.ACRONYM_WITHOUT_DEFINITIONS_NAME] - empty list without definitions
 * * [AcronymsApiTestInterceptor.ACRONYM_WITH_CRASH_NAME] - throws general exception
 */
class AcronymsApiTestInterceptor : Interceptor {

    companion object {

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022

        const val ACRONYM_WITH_DEFINITIONS_NAME: String = "ACRONYM_WITH_DEFINITIONS"
        val ACRONYM_DEFINITIONS: List<AcronymDefinition> = listOf(
            AcronymDefinition(
                name = DEFINITION_NAME,
                frequency = DEFINITION_FREQUENCY,
                year = DEFINITION_YEAR,
                variations = emptyList(),
            ),
        )

        const val ACRONYM_WITHOUT_DEFINITIONS_NAME: String = "ACRONYM_WITHOUT_DEFINITIONS"
        const val ACRONYM_WITH_CRASH_NAME: String = "ACRONYM_WITH_CRASH"

        private const val CONTENT_TYPE_KEY: String = "content-type"
        private const val CONTENT_TYPE_VALUE: String = "application/json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val uri: String = request.url.toString()

        val jsonResponse: String = when {
            uri.endsWith(ACRONYM_WITH_DEFINITIONS_NAME) -> {
                """
                    [
                      {
                        "sf": "$ACRONYM_WITH_DEFINITIONS_NAME",
                        "lfs": [
                          {
                            "lf": "$DEFINITION_NAME",
                            "freq": $DEFINITION_FREQUENCY,
                            "since": $DEFINITION_YEAR,
                            "vars": []
                          }
                        ]
                      }
                    ]
                """.trimIndent()
            }
            uri.endsWith(ACRONYM_WITHOUT_DEFINITIONS_NAME) -> {
                "[]"
            }
            uri.endsWith(ACRONYM_WITH_CRASH_NAME) -> {
                "Crash for testing"
            }
            else -> {
                "[]"
            }
        }
        return chain.proceed(request).newBuilder()
            .code(200)
            .message(jsonResponse)
            .protocol(Protocol.HTTP_2)
            .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
            .body(jsonResponse.toResponseBody(CONTENT_TYPE_VALUE.toMediaTypeOrNull()))
            .build()
    }
}
