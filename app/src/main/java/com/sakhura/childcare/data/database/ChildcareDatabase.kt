package com.sakhura.childcare.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sakhura.childcare.data.database.dao.CareSessionDao
import com.sakhura.childcare.data.database.dao.ChildDao
import com.sakhura.childcare.data.database.dao.ParentDao
import com.sakhura.childcare.data.database.entities.CareSession
import com.sakhura.childcare.data.database.entities.Child
import com.sakhura.childcare.data.database.entities.Parent

@Database(
    entities = [Child::class, Parent::class, CareSession::class],
    version = 1,
    exportSchema = false
)
abstract class ChildcareDatabase : RoomDatabase() {
    abstract fun childDao(): ChildDao
    abstract fun parentDao(): ParentDao
    abstract fun careSessionDao(): CareSessionDao

    companion object {
        @Volatile
        private var INSTANCE: ChildcareDatabase? = null

        fun getDatabase(context: Context): ChildcareDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChildcareDatabase::class.java,
                    "childcare_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}