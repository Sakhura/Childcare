package com.sakhura.childcare.di

import android.content.Context
import androidx.room.Room
import com.sakhura.childcare.data.database.dao.ParentDao
import com.sakhura.childcare.data.database.entities.CareSession
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun  provideChildcareDatabase(@ApplicationContext context: Context): ChildcareDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            ChildcareDatabase::class.java,
            "childcare_database"
        ).build()
    }

    @Provides
    fun provideChildDao(database: ChildcareDatabase): ChildDao{
        return database.childDao()
    }

    @Provides
    fun provideParentDao(database: ChildcareDatabase): ParentDao{
                return database.parentDao()
    }

    @Provides
    fun provideCareSessionDao(database: ChildcareDatabase): CareSessionDao{
        return database.scheduleDao()
    }

}