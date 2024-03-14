package com.example.proyecto.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(

    tableName = "tarea",
    foreignKeys = [
        ForeignKey(
            entity = Prioridad::class,
            parentColumns = ["prioridad"],
            childColumns = ["prioridadTarea"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("prioridadTarea")]
)

data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val descripcion: String,
    val fechaCreacion: String,
    val prioridadTarea: String,
    var completada : Boolean = false
)

@Entity(tableName = "prioridad", indices = [Index(value = ["prioridad"], unique = true)])

data class Prioridad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val prioridad: String

)

