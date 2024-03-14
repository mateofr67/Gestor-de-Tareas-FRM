package com.example.proyecto.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Tarea::class, Prioridad::class],
    version = 1,
    exportSchema = true
)
abstract class TareaDatabase: RoomDatabase() {

    abstract fun tareaDao(): TareaDAO

    companion object {
        @Volatile
        private var INSTANCE: TareaDatabase? = null


        fun getDataBase(context: Context): TareaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareaDatabase::class.java,
                    "tareas_database5"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    val prioridadesPorDefecto = listOf(
                                        Prioridad(1, "Alta"),
                                        Prioridad(2, "Media"),
                                        Prioridad(3, "Baja")
                                    )
                                    database.tareaDao()
                                        .insertAll(prioridades = prioridadesPorDefecto)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
