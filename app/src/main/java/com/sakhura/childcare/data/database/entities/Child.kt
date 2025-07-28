package com.sakhura.childcare.data.database.entities

@Entity(tableName = "children")
data class Child(
    @PrimareyKey(autoGenerate = true)
    val id: Long= 0,
    val name : String,
    val age: Int,
    val hourltyRate : Double
    val notes : String = "",
    val createdAt : Long = System.currentTimeMillis()
)