package com.samwrotethecode.translator.dictionary_screen.data.remote

import com.samwrotethecode.translator.dictionary_screen.data.remote.dto.WordDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("api/v2/entries/en/{word}")
    suspend fun getWordDefinition(@Path("word") word: String): List<WordDto>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }
}
