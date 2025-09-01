package com.samwrotethecode.translator.home_screen.di

import com.samwrotethecode.translator.home_screen.data.service.LanguageDetectorImpl
import com.samwrotethecode.translator.home_screen.data.service.LanguageTranslatorImpl
import com.samwrotethecode.translator.home_screen.domain.service.LanguageDetector
import com.samwrotethecode.translator.home_screen.domain.service.LanguageTranslator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideLanguageDetector(): LanguageDetector = LanguageDetectorImpl()

    @Singleton
    @Provides
    fun provideLanguageTranslator(): LanguageTranslator = LanguageTranslatorImpl()
}