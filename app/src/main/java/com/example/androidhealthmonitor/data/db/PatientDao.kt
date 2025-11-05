package com.example.androidhealthmonitor.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PatientDao {
    @Query("SELECT * FROM patients ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Patient>>


    @Insert
    suspend fun insert(patient: Patient): Long


    @Delete
    suspend fun delete(patient: Patient)


    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun findById(id: Long): Patient?
}