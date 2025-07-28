package com.sakhura.childcare.data.database.dao

import androidx.room.Delete
import com.sakhura.childcare.data.database.entities.Child
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao{
    @Query("SELECT * FROM children ORDER BY name ASC")
    fun getAllChildren(): Flow<List<Child>>

    @Query("SELECT * FROM children WHERE id = :id")
    fun getChildById(id: Long): Flow<Child>

    @Insert
    suspend fun insertChild(child: Child)

    @Update
    suspend fun updateChild(child: Child)

    @Delete
    suspend fun deleteChild(child: Child)

}