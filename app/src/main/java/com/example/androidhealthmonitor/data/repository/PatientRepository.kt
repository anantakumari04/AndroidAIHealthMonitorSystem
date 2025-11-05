package com.example.androidhealthmonitor.data.repository


import com.example.androidhealthmonitor.data.db.Patient
import com.example.androidhealthmonitor.data.db.PatientDao
import kotlinx.coroutines.flow.Flow


class PatientRepository(private val dao: PatientDao) {
    fun getAll(): Flow<List<Patient>> = dao.getAll()
    suspend fun insert(p: Patient) = dao.insert(p)
    suspend fun delete(p: Patient) = dao.delete(p)
    suspend fun findById(id: Long) = dao.findById(id)
}