package com.example.proyecto.views.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.proyecto.R
import com.example.proyecto.data.database.Tarea
import com.example.proyecto.databinding.FragmentItemTareaBinding
import com.example.proyecto.views.viewmodel.TareaViewModel


class TareasListAdapter(private val viewModel: TareaViewModel):
    ListAdapter<Tarea, TareasListAdapter.ViewHolder>(ModuleDiffCallback) {


    companion object {
        private val ModuleDiffCallback = object : DiffUtil.ItemCallback<Tarea>() {
            override fun areContentsTheSame(oldItem: Tarea, newItem: Tarea): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Tarea, newItem: Tarea): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemTareaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(val binding: FragmentItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarea: Tarea) {
            binding.tareaId.text = tarea.id.toString()
            binding.tareaDescripcion.text = tarea.descripcion
            binding.tareaFecha.text = tarea.fechaCreacion
            binding.tareaPrioridad.text = tarea.prioridadTarea

            actualizarIcono(tarea)



            binding.tareaCompletada.setOnClickListener {
                tarea.completada = !tarea.completada

                viewModel.marcarComoCompletada(tarea)

                actualizarIcono(tarea)
            }


        }

        private fun actualizarIcono(tarea: Tarea) {
            if (tarea.completada) {
                binding.tareaCompletada.setImageResource(R.drawable.checked)
            } else {
                binding.tareaCompletada.setImageResource(R.drawable.unchecked)
            }
        }
    }

}