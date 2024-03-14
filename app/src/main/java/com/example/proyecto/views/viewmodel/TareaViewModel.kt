package com.example.proyecto.views.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.proyecto.app.TareaApp
import com.example.proyecto.data.AppRepository
import com.example.proyecto.data.PreferencesRepository
import com.example.proyecto.data.database.Prioridad

import com.example.proyecto.data.database.Tarea
import kotlinx.coroutines.launch

class TareaViewModel(private val appRepository: AppRepository,  private val preferencesRepository: PreferencesRepository) : ViewModel() {

    val allTareas: LiveData<List<Tarea>> = appRepository.allTareas
    val allPrioridades: LiveData<List<Prioridad>> = appRepository.allPrioridades


    fun borrarTareas() {
        viewModelScope.launch {
            appRepository.borrarTareas()
        }

    }

    fun cambiarModoNocturno(){
        viewModelScope.launch {
            preferencesRepository.cambiarModoNocturno()
        }
    }


    fun getTareasCompletadas(): LiveData<List<Tarea>> {
        return appRepository.getTareasCompletadas()
    }


    val preferencias = preferencesRepository.userPreferencesFlow.asLiveData()


    fun getTareasPorCompletar(): LiveData<List<Tarea>> {
        return appRepository.getTareasPorCompletar()
    }


    fun insertTarea(
        categoriaImportancia: String,
        Descripcion: String,
        Fecha: String,
        Completada: Boolean
    ) {
        viewModelScope.launch {
            appRepository.insertTarea(Descripcion, Fecha, categoriaImportancia, Completada)
        }
    }

    fun marcarComoCompletada(tarea: Tarea) {
        tarea.completada = !tarea.completada

        viewModelScope.launch {
            appRepository.updateTareaCompletada(tarea)
        }
    }



    }



class TareasViewModelFactory(): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as TareaApp

        if(modelClass.isAssignableFrom(TareaViewModel::class.java))
            return TareaViewModel(app.appRepository, app.dataStoreRepository) as T
        throw IllegalArgumentException("Error al instanciar el ViewModel")
    }
}