package com.example.androidhealthmonitor.data.db


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val notes: String,
    val temperature: Double,
    val heartRate: Int,
    val systolic: Int,
    val diastolic: Int,
    val weightKg: Double,
    val heightCm: Double,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun bmi(): Double {
        val m = heightCm / 100.0
        return if (m <= 0.0) 0.0 else weightKg / (m * m)
    }
}