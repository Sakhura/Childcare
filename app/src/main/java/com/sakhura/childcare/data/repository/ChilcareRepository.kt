package com.sakhura.childcare.data.repository

import com.sakhura.childcare.data.database.dao.ChildDao
import com.sakhura.childcare.data.database.dao.ParentDao
import com.sakhura.childcare.data.database.entities.CareSession
import kotlinx.coroutines.NonDisposableHandle.parent

class ChilcareRepository (
    private val childDao: ChildDao,
    private val parentDao: ParentDao,
    private val careSessionDao: CareSessionDao

){
    fun getAllChildren() = childDao.getAllChildren()

    suspend fun getChildById(id: Long) = childDao.getChildById(id)

    suspend fun insertChild(child: Child, parents: List<Parent>): Long
    val childId = childDao.insertChild(child)
    parents.forEach
    {
        parent ->
        parentDao.insertParent(parent.copy(childId = childId))
    }
    return childId
}

suspend fun getParentsByChildId(childId: Long) = parentDao.getParentsForChild(childId)

suspend fun starCareSession(childId: Long, startTime: Long) = careSessionDao.insertCareSession()
 val careSessionId = CareSession(
     
 )