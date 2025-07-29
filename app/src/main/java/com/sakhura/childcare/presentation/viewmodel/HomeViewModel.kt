package com.sakhura.childcare.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakhura.childcare.data.database.entities.CareSession
import com.sakhura.childcare.data.database.entities.Child
import com.sakhura.childcare.data.repository.ChildcareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ChildcareRepository
) : ViewModel() {

    val children: Flow<List<Child>> = repository.getAllChildren()

    private val _activeSession = MutableStateFlow<CareSession?>(null)
    val activeSession: StateFlow<CareSession?> = _activeSession.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadActiveSession()
    }

    private fun loadActiveSession() {
        viewModelScope.launch {
            try {
                _activeSession.value = repository.getActiveSession()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar sesión activa: ${e.message}"
            }
        }
    }

    fun startCareSession(childId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Verificar si ya hay una sesión activa
                val currentActiveSession = repository.getActiveSession()
                if (currentActiveSession != null) {
                    _errorMessage.value = "Ya hay una sesión de cuidado activa"
                    return@launch
                }

                // El repository automáticamente obtiene la tarifa del niño
                val sessionId = repository.startCareSession(childId)
                loadActiveSession()
            } catch (e: Exception) {
                _errorMessage.value = "Error al iniciar sesión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun endCareSession(notes: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val activeSessionValue = _activeSession.value
                if (activeSessionValue != null) {
                    repository.endCareSession(activeSessionValue.id, notes)
                    loadActiveSession()
                } else {
                    _errorMessage.value = "No hay sesión activa para finalizar"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al finalizar sesión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}