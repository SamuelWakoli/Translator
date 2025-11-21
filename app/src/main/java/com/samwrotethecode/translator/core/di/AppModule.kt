package com.samwrotethecode.translator.core.di

import android.content.Context
import androidx.room.Room
import com.samwrotethecode.translator.core.data.local.AppDatabase
import com.samwrotethecode.translator.core.data.local.dao.TranslationHistoryDao
import com.samwrotethecode.translator.core.data.repository.TranslationHistoryRepositoryImpl
import com.samwrotethecode.translator.core.domain.repository.TranslationHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "translator_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTranslationHistoryDao(database: AppDatabase): TranslationHistoryDao {
        return database.translationHistoryDao()
    }

    @Provides
    @Singleton
    fun provideTranslationHistoryRepository(dao: TranslationHistoryDao): TranslationHistoryRepository {
        return TranslationHistoryRepositoryImpl(dao)
    }
}
