package com.example.proyecto.views.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.proyecto.data.database.Prioridad
import com.example.proyecto.databinding.FragmentItemPrioridadBinding


/* ListAdapter de Prioridades de Tareas */

class PrioridadesListAdapter(): ListAdapter<Prioridad, PrioridadesListAdapter.ViewHolder>(ModuleDiffCallback) {

    companion object{
        private val ModuleDiffCallback = object: DiffUtil.ItemCallback<Prioridad>(){
            override fun areContentsTheSame(oldItem: Prioridad, newItem: Prioridad): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Prioridad, newItem: Prioridad): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return  ViewHolder(
            FragmentItemPrioridadBinding.inflate(
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

    inner class ViewHolder(val binding: FragmentItemPrioridadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(prioridad: Prioridad){
            binding.prioridadID.text = prioridad.id.toString()
            binding.prioridad.text = prioridad.prioridad

        }
    }

}