package com.example.proyecto.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.databinding.FragmentPrioridadListBinding
import com.example.proyecto.views.adapter.PrioridadesListAdapter
import com.example.proyecto.views.viewmodel.TareaViewModel
import com.example.proyecto.views.viewmodel.TareasViewModelFactory


class PrioridadListFragment : Fragment() {
    private var _binding: FragmentPrioridadListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TareaViewModel by activityViewModels{ TareasViewModelFactory() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPrioridadListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listPrioridades.layoutManager = LinearLayoutManager(context)
        val adapter = PrioridadesListAdapter()
        binding.listPrioridades.adapter = adapter

        viewModel.allPrioridades.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }
}