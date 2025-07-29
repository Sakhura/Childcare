package com.sakhura.childcare.di

import android.content.Context
import androidx.room.Room
import com.sakhura.childcare.data.database.ChildcareDatabase
import com.sakhura.childcare.data.database.dao.CareSessionDao
import com.sakhura.childcare.data.database.dao.ChildDao
import com.sakhura.childcare.data.database.dao.ParentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideChildcareDatabase(@ApplicationContext context: Context): ChildcareDatabase {
        return Room.databaseBuilder(
            context,
            ChildcareDatabase::class.java,
            "childcare_database"
        ).build()
    }

    @Provides
    fun provideChildDao(database: ChildcareDatabase): ChildDao {
        return database.childDao()
    }

    @Provides
    fun provideParentDao(database: ChildcareDatabase): ParentDao {
        return database.parentDao()
    }

    @Provides
    fun provideCareSessionDao(database: ChildcareDatabase): CareSessionDao {
        return database.careSessionDao()
    }
}