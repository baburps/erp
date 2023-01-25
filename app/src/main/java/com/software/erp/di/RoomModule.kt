package com.software.erp.di

import android.content.Context
import androidx.room.Room
import com.software.erp.domain.room.ERPRoomDatabase
import com.software.erp.domain.room.YarnPurchaseRoomDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideNotesDAO(appDatabase: ERPRoomDatabase): YarnPurchaseRoomDAO {
        return appDatabase.yarnPurchaseDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ERPRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            ERPRoomDatabase::class.java,
            ERPRoomDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}