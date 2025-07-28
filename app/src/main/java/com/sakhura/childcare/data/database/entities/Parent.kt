package com.sakhura.childcare.data.database.entities

@Entity(tableName = "parents")
data class Parent(
    @PrimareyKey(autoGenerate = true)
    val id: Long= 0,
    val childId : Long,
    val name : String,
    val phoneNumber : String,
    val relationship : String = "Padre / Madre / Tutor"
)