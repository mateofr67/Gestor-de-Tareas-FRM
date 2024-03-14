package com.example.proyecto.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.databinding.FragmentTareasCompletadasListBinding
import com.example.proyecto.views.adapter.TareasListAdapter
import com.example.proyecto.views.viewmodel.TareaViewModel

class TareasCompletadasFragment : Fragment() {

    private var _binding: FragmentTareasCompletadasListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TareaViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTareasCompletadasListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tareaAdapter = TareasListAdapter(viewModel)

        binding.listCompletadas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tareaAdapter
        }

        viewModel.getTareasCompletadas().observe(viewLifecycleOwner) { tareas ->
            tareaAdapter.submitList(tareas)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}