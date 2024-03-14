package com.example.proyecto.views

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.R
import com.example.proyecto.databinding.FragmentTareaListBinding
import com.example.proyecto.views.adapter.TareasListAdapter
import com.example.proyecto.views.viewmodel.TareaViewModel
import com.example.proyecto.views.viewmodel.TareasViewModelFactory


/**
 * A fragment representing a list of Items.
 */
class TareaListFragment : Fragment() {
    private var _binding: FragmentTareaListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer


    private val viewModel: TareaViewModel by activityViewModels { TareasViewModelFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTareaListBinding.inflate(inflater, container, false)
        mediaPlayer = MediaPlayer.create(requireContext(),R.raw.papel)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddTarea.setOnClickListener {
            findNavController().navigate(TareaListFragmentDirections.actionTareaListFragmentToAddTareaFragment())
        }



        binding.listTareas.layoutManager = LinearLayoutManager(context)
        val adapter = TareasListAdapter(viewModel)
        binding.listTareas.adapter = adapter

        viewModel.allTareas.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }



        binding.btnClearTareas.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle(getString(R.string.confirmacion_borrado_title))
            alertDialogBuilder.setMessage(getString(R.string.confirmacion_borrado_mensaje))
            alertDialogBuilder.setCancelable(false)

            alertDialogBuilder.setPositiveButton(getString(R.string.confirmacion_borrado_aceptar)) { dialog, _ ->
                viewModel.borrarTareas()
                mediaPlayer.start()
                mostrarToast(getString(R.string.tareas_borrado_exito_mensaje))
                dialog.dismiss()
            }

            alertDialogBuilder.setNegativeButton(getString(R.string.confirmacion_borrado_cancelar)) { dialog, _ ->
                dialog.cancel()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun mostrarToast(mensaje: String) {
        Toast.makeText(activity,mensaje, Toast.LENGTH_SHORT).show()
    }
}