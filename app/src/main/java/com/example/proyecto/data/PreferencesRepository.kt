package com.example.proyecto.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.proyecto.views.viewmodel.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesRepository(val dataStore: DataStore<Preferences>) {


    private object PreferencesKeys {
        val MODO_NOCTURNO = booleanPreferencesKey("modo_nocturno")
    }

    companion object {
        val TAG = "PreferencesRepository"
    }


    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                exception.printStackTrace()
            }
        }
        .map { preferences ->
            val modoNocturno = preferences[PreferencesKeys.MODO_NOCTURNO] ?: false
            UserPreferences(modoNocturno)
        }



    suspend fun cambiarModoNocturno() {
        try {
            dataStore.edit { preferences ->
                val valorActual = preferences[PreferencesKeys.MODO_NOCTURNO] ?: false
                preferences[PreferencesKeys.MODO_NOCTURNO] = !valorActual
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}