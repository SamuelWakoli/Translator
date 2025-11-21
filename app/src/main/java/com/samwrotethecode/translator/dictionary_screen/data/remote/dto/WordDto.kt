package com.samwrotethecode.translator.dictionary_screen.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.samwrotethecode.translator.dictionary_screen.presentation.DictionaryDefinition
import com.samwrotethecode.translator.dictionary_screen.presentation.Meaning
import com.samwrotethecode.translator.dictionary_screen.presentation.Definition

data class WordDto(
    val word: String,
    val phonetic: String?,
    val phonetics: List<PhoneticDto>?,
    val meanings: List<MeaningDto>
) {
    fun toDictionaryDefinition(): DictionaryDefinition {
        return DictionaryDefinition(
            word = word,
            phonetic = phonetic ?: phonetics?.firstOrNull { !it.text.isNullOrBlank() }?.text,
            meanings = meanings.map { it.toMeaning() }
        )
    }
}

data class PhoneticDto(
    val text: String?,
    val audio: String?
)

data class MeaningDto(
    val partOfSpeech: String,
    val definitions: List<DefinitionDto>
) {
    fun toMeaning(): Meaning {
        return Meaning(
            partOfSpeech = partOfSpeech,
            definitions = definitions.map { it.toDefinition() }
        )
    }
}

data class DefinitionDto(
    val definition: String,
    val example: String?,
    val synonyms: List<String>?,
    val antonyms: List<String>?
) {
    fun toDefinition(): Definition {
        return Definition(
            definition = definition,
            example = example
        )
    }
}
