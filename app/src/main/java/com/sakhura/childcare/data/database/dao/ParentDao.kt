package com.sakhura.childcare.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sakhura.childcare.data.database.entities.Parent
import kotlinx.coroutines.flow.Flow

@Dao
interface ParentDao {
    @Query("SELECT * FROM parents WHERE childId = :childId")
    fun getParentsForChild(childId: Long): Flow<List<Parent>>

    // Agregado método que falta en el repositorio
    @Query("SELECT * FROM parents WHERE childId = :childId")
    fun getParentsByChildId(childId: Long): Flow<List<Parent>>

    @Insert
    suspend fun insertParent(parent: Parent)

    @Update
    suspend fun updateParent(parent: Parent)

    @Delete
    suspend fun deleteParent(parent: Parent)
}