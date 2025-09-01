<img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp" width="100" alt="Logo"/>

# Translator

**Translator** is an Android application demonstrating on-device machine learning capabilities for
language translation. It leverages Google's ML Kit to provide seamless and efficient language
detection and translation.

## Features

* **Language Detection:** Automatically identifies the language of the input text.
* **Text Translation:** Translates text into a wide variety of target languages.
* **On-Device ML:** Utilizes ML Kit's on-device models for fast and offline-capable translation.

## Tech Stack

* **Kotlin:** Primary programming language.
* **Jetpack Compose:** Modern UI toolkit for building native Android UIs.
* **ML Kit:**
    * Language ID
    * Translation
* **Hilt:** For dependency injection.
* **Coroutines & Flow:** For asynchronous operations.

## Screenshots

<img src="screenshots/language_detection.png"
width="300" height="600" alt="Screenshot showing the Translator app with English text auto-detected"
title="Language Detection Screen">

<img src="screenshots/model_download.png"
width="300" height="600" alt="Screenshot illustrating the app downloading the ML model for Afrikaans
translation" title="Model Download Screen">

<img src="screenshots/translation_afrikaans.png"
width="300" height="600" alt="Screenshot of translated text in Afrikaans" title="Translated Text in
Afrikaans">

<img src="screenshots/translation_chinese.png"
width="300" height="600" alt="Screenshot of translated text in Chinese" title="Translated Text in
Chinese">

<img src="screenshots/language_selection.png"
width="300" height="600" alt="Screenshot of the target language selection menu with 59 languages"
title="Language Selection Menu">
