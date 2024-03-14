package com.example.proyecto.app

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.proyecto.data.AppRepository
import com.example.proyecto.data.PreferencesRepository
import com.example.proyecto.data.database.TareaDatabase

const val TAREA_PREFERENCES = "preferencias_tareas"
private val Context.dataStorePreferencias by preferencesDataStore(
    name = TAREA_PREFERENCES
)

class TareaApp : Application() {
    private val tareaDatabase: TareaDatabase by lazy { TareaDatabase.getDataBase(this) }
    val appRepository: AppRepository by lazy { AppRepository(tareaDatabase.tareaDao()) }
    lateinit var dataStoreRepository: PreferencesRepository

    override fun onCreate() {
        super.onCreate()
        dataStoreRepository = PreferencesRepository(dataStorePreferencias)
    }
}

