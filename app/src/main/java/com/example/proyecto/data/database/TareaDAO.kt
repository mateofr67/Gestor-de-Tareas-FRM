package com.example.proyecto.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface TareaDAO {


    @Query("SELECT * FROM tarea WHERE completada = :completada")
    fun getTareasCompletadas(completada: Boolean): LiveData<List<Tarea>>

    @Query("SELECT * FROM tarea")
    fun getAllTareas(): LiveData<List<Tarea>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTarea(tarea: Tarea): Long

    @Update
    suspend fun updateTarea(tarea: Tarea)

    @Query("DELETE FROM tarea")
    suspend fun borrarTareas()

    @Update
    suspend fun updateTareaCompletada(tarea: Tarea)


    @Query("SELECT * FROM prioridad")
    fun getAllPrioridades(): LiveData<List<Prioridad>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(prioridades: List<Prioridad>)



}