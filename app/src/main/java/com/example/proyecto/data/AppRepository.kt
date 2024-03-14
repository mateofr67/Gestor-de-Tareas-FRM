package com.example.proyecto.data

import androidx.lifecycle.LiveData
import com.example.proyecto.data.database.Prioridad
import com.example.proyecto.data.database.Tarea
import com.example.proyecto.data.database.TareaDAO

class AppRepository(private val tareaDao: TareaDAO)  {
    val allTareas = tareaDao.getAllTareas()
    val allPrioridades = tareaDao.getAllPrioridades()

    fun getTareasCompletadas(): LiveData<List<Tarea>> {
        return tareaDao.getTareasCompletadas(true)
    }

    fun getTareasPorCompletar(): LiveData<List<Tarea>> {
        return tareaDao.getTareasCompletadas(false)
    }

    suspend fun insertTarea(tareaDescripcion: String, tareaFecha: String, prioridadImportancia: String, completadaTarea: Boolean): Long {
        val tarea = Tarea(descripcion = tareaDescripcion, fechaCreacion = tareaFecha, prioridadTarea = prioridadImportancia  , completada = completadaTarea)
        return tareaDao.insertTarea(tarea)
    }



    suspend fun updateTareaCompletada(tarea: Tarea) {
        tarea.completada = !tarea.completada
        tareaDao.updateTarea(tarea)
    }


    suspend fun borrarTareas() {
        tareaDao.borrarTareas()
    }




}