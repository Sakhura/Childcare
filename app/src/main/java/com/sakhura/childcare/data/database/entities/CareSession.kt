package com.sakhura.childcare.data.database.entities

@Entity(tableName= "care_sessions")
data class CareSession (
    @PrimareyKey(autoGenerate = true)
    val id: Long = 0,
    val chilId : Long,
    val starTime: Long,
    val endTime : Long?,
    val hourlyRate : Double,
    val notes : String = "",
    val isCompleted : Boolean = false
){
    fun getTotalHours(): Double{
        return if(endTime != null){
            (endTime - starTime)/ 3600000
        }else 0.0
            }

    fun getTotalCost(): Double{
        return getTotalHours() * hourlyRate
    }

}