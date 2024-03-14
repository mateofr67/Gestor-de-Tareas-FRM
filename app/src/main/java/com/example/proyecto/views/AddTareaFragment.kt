package com.example.proyecto.views


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.proyecto.R
import com.example.proyecto.databinding.FragmentAddTareaBinding
import com.example.proyecto.views.viewmodel.TareaViewModel
import com.example.proyecto.views.viewmodel.TareasViewModelFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale



class AddTareaFragment : Fragment() {
    private var _binding: FragmentAddTareaBinding? = null
    private val binding get() = _binding!!
    private val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    private lateinit var mediaPlayer: MediaPlayer

 private val viewmodel : TareaViewModel by activityViewModels<TareaViewModel>
 { TareasViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTareaBinding.inflate(inflater,container,false)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.anyadirtarea)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = binding.spinnerCategoria
        val categorias = arrayOf("Alta", "Media", "Baja")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        binding.btnGuardarTarea.setOnClickListener {
            val descripcionTarea: String = binding.TareaDescripcion.text.toString()

            if (descripcionTarea.isBlank()) {

                Toast.makeText(context, "El campo de descripción no puede estar vacío", Toast.LENGTH_SHORT).show()
            } else {
                val currentTimestamp = Timestamp(System.currentTimeMillis())
                val fecha = formato.format(currentTimestamp)

                viewmodel.insertTarea(
                    spinner.selectedItem.toString(),
                    descripcionTarea,
                    fecha,
                    false
                )
                mediaPlayer.start()
                Toast.makeText(context, "Tarea insertada con éxito", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}