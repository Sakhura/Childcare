package com.sakhura.childcare.data.repository

import com.sakhura.childcare.data.database.dao.ChildDao
import com.sakhura.childcare.data.database.dao.ParentDao
import com.sakhura.childcare.data.database.entities.CareSession
import com.sakhura.childcare.data.database.entities.Child
import kotlinx.coroutines.NonDisposableHandle.parent

class ChildcareRepository(
    private val childDao: ChildDao,
    private val parentDao: ParentDao,
    private val careSessionDao: CareSessionDao
) {
    fun getAllChildren() = childDao.getAllChildren()

    suspend fun getChildById(id: Long) = childDao.getChildById(id)

    suspend fun insertChild(child: Child, parents: List<Parent>): Long {
        val childId = childDao.insertChild(child)
        parents.forEach { parent ->
            parentDao.insertParent(parent.copy(childId = childId))
        }
        return childId
    }

    suspend fun getParentsByChildId(childId: Long) = parentDao.getParentsByChildId(childId)

    suspend fun startCareSession(childId: Long, hourlyRate: Double): Long {
        val session = CareSession(
            childId = childId,
            startTime = System.currentTimeMillis(),
            endTime = null,
            hourlyRate = hourlyRate
        )
        return careSessionDao.insertSession(session)
    }

    suspend fun endCareSession(sessionId: Long, notes: String) {
        val session = careSessionDao.getActiveSession()
        session?.let {
            val updatedSession = it.copy(
                endTime = System.currentTimeMillis(),
                notes = notes,
                isCompleted = true
            )
            careSessionDao.updateSession(updatedSession)
        }
    }

    suspend fun getSessionsByChildId(childId: Long) = careSessionDao.getSessionsByChildId(childId)

    suspend fun getActiveSession() = careSessionDao.getActiveSession()

    suspend fun getTotalEarningsByChild(childId: Long) = careSessionDao.getTotalEarningsByChild(childId) ?: 0.0
}