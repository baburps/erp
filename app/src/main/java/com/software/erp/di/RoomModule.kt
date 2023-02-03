package com.software.erp.di

import android.content.Context
import androidx.room.Room
import com.software.erp.base.ERPApplication
import com.software.erp.domain.room.ERPRoomDAO
import com.software.erp.domain.room.ERPRoomDatabase
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
    fun provideNotesDAO(appDatabase: ERPRoomDatabase): ERPRoomDAO {
        return appDatabase.yarnPurchaseDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ERPRoomDatabase {
        return Room.databaseBuilder(
            appContext ,
            ERPRoomDatabase::class.java ,
            ERPRoomDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): ERPApplication {
        return app as ERPApplication
    }
}