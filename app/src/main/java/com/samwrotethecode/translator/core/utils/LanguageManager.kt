package com.samwrotethecode.translator.core.utils

import java.util.Locale

fun getLanguageNameFromBcp47(
    bcp47Code: String,
    displayLocale: Locale = Locale.getDefault()
): String {
    val locale = Locale.forLanguageTag(bcp47Code)
    return locale.getDisplayLanguage(displayLocale)
}