package com.diegoginko.climapp.ui.pronosticoExtendido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegoginko.climapp.R
import com.diegoginko.climapp.adapter.ExtendidoAdapter
import com.diegoginko.climapp.adapter.ExtendidoClickListener
import com.diegoginko.climapp.databinding.PronosticoExtendidoFragmentBinding

class PronosticoExtendidoFragment  : Fragment(){
    private lateinit var pronosticoExtendidoViewModel : PronosticoExtendidoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : PronosticoExtendidoFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.pronostico_extendido_fragment, container, false
        )
        val application = requireNotNull(this.activity).application

        val ciudadSeleccionada = PronosticoExtendidoFragmentArgs.fromBundle(arguments!!).ciudadSeleccionada

        val viewModelFactory = PronosticoExtendidoViewModelFactory(ciudadSeleccionada, application)
        pronosticoExtendidoViewModel = ViewModelProviders.of(this, viewModelFactory).get(PronosticoExtendidoViewModel::class.java)

        binding.viewmodel = pronosticoExtendidoViewModel

        val manager = LinearLayoutManager(activity)
        binding.rvListExtendido.layoutManager = manager

        val adapter = ExtendidoAdapter(ExtendidoClickListener {
            extendido ->
            pronosticoExtendidoViewModel.onExtendidoClicked(extendido)
        })
        binding.rvListExtendido.adapter = adapter

        pronosticoExtendidoViewModel.listaPronostico.observe(viewLifecycleOwner, Observer {
            //Actualiza la vista cuando cambia el listado
            it?.let {
                adapter.submitList(it)
            }
        })


        binding.setLifecycleOwner(this)







        return binding.root
    }
}