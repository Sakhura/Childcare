package com.sakhura.childcare.data.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sakhura.childcare.data.database.dao.ChildDao
import com.sakhura.childcare.data.database.dao.ParentDao
import com.sakhura.childcare.data.database.entities.CareSession
import com.sakhura.childcare.data.database.entities.Parent

@Database(
    entities = [Child::class, Parent::class, CareSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ChildCareDatabase : RoomDatabase() {
    abstract fun childDao(): ChildDao
    abstract fun parentDao(): ParentDao
    abstract fun careSessionDao(): CareSessionDao

    companion object{
        @Volatile
        private var INSTANCE: ChildCareDatabase? = null

        fun getDatabase(context: Context): ChildCareDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChildCareDatabase::class.java,
                    "childcare_database"
                ).build()
                INSTANCE = instance
                instance
            }
            }
    }
}