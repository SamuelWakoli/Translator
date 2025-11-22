# Project Plan: Modern Android Translator App

## Goal
Build a premium, modern, and adaptive Android translator app that justifies a $1 price point through superior UX, privacy, and feature set.

## Core Philosophy
- **Modern & Neat**: Material 3 Design, Dynamic Colors, Smooth Animations.
- **Adaptive**: Seamless experience on Phones, Foldables, and Tablets.
- **Privacy-Focused**: On-device translation where possible, no data tracking.
- **User-Centric**: Features that solve real problems (Dictionary, Conversation Mode).

## Sprints

### Sprint 1: Foundation & Core Experience (Completed)
- [x] **Adaptive Navigation Architecture**
    - Implement `NavigationSuiteScaffold` or custom logic for:
        - Bottom Navigation (Compact - Phones)
        - Navigation Rail (Medium - Foldables/Tablets in portrait)
        - Permanent Navigation Drawer (Expanded - Tablets landscape/Desktop)
    - Handle Window Size Classes.
- [x] **Enhanced Home Screen**
    - Clean text input area with auto-detect language.
    - Quick language swap.
    - "Paste" and "Copy" quick actions.
- [x] **Local History & Favorites**
    - [x] Room Database integration (Implemented).
    - [x] Basic History Screen (Implemented).
    - [x] **Add**: Search and Filter history items.
    - [x] **Add**: Ability to "Star" translations (Favorites/Phrasebook foundation).
    - [x] **Dictionary Page (MVP)**
    - Dedicated screen for looking up words.
    - Definitions, synonyms, and usage examples.
    - "Word of the Day" card for engagement.

### Sprint 2: Voice & Conversation
- [ ] **Speech-to-Text (STT)**
    - Microphone input for instant translation.
- [ ] **Text-to-Speech (TTS)**
    - High-quality audio playback for translations.
- [ ] **Conversation Mode**
    - Split-screen UI for two-way dialogue.
    - Auto-listening mode (if feasible with on-device APIs).
    - "Face-to-Face" view (flip text for the other person).

### Sprint 3: Visual Intelligence
- [ ] **Camera Translation**
    - Live camera preview with text overlay (AR-like).
    - ML Kit Vision integration.
- [ ] **Image Translation**
    - Pick image from gallery to translate text.

### Sprint 4: Polish & Premium Features
- [ ] **Theming & Customization**
    - Dark/Light mode toggle.
    - App icon variants.
    - Font size adjustments.
- [ ] **Offline Mode Management**
    - UI to download/delete offline language models.
- [ ] **Onboarding & "About"**
    - Slick onboarding carousel explaining features.
    - "About App" page with changelog and developer info.

## Technical Considerations
- **Architecture**: MVVM + Clean Architecture (Domain/Data/Presentation).
- **DI**: Hilt.
- **Local Data**: Room Database.
- **Network**: Retrofit (for Dictionary API if needed).
- **ML**: Google ML Kit (On-device Translation).
- **UI**: Jetpack Compose + Material 3.
