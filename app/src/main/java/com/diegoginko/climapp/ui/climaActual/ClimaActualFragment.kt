package com.diegoginko.climapp.ui.climaActual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegoginko.climapp.R
import com.diegoginko.climapp.adapter.CiudadAdapter
import com.diegoginko.climapp.adapter.CiudadClickListener
import com.diegoginko.climapp.adapter.CiudadLongClickListener
import com.diegoginko.climapp.databinding.ClimaActualFragmentBinding
import kotlinx.android.synthetic.main.agregar_ciudad_dialog.view.*
import kotlinx.android.synthetic.main.clima_actual_fragment.*

class ClimaActualFragment : Fragment(){

    private var alertDialogCiudad : AlertDialog? = null
    private lateinit var climaActualViewModel : ClimaActualViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : ClimaActualFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.clima_actual_fragment, container, false
        )
        val application = requireNotNull(this.activity).application

        val viewModelFactory = ClimaActualViewModelFactory(application)

        climaActualViewModel = ViewModelProviders.of(this, viewModelFactory).get(ClimaActualViewModel::class.java)

        binding.climaActualViewModel = climaActualViewModel

        val manager = LinearLayoutManager(activity)

        binding.climaActualList.layoutManager = manager
        //climaActual_list.layoutManager = manager

        val adapter = CiudadAdapter(CiudadClickListener{
            ciudad ->
            climaActualViewModel.onCiudadClicked(ciudad)
        }, CiudadLongClickListener {
            ciudad ->
            climaActualViewModel.onCiudadLongClicked(ciudad)
        })

        //climaActual_list.adapter = adapter
        binding.climaActualList.adapter = adapter

        climaActualViewModel.listCiudades.observe(viewLifecycleOwner, Observer {
            it?.let {
                //TODO: arreglar como actualiza
                //climaActualViewModel.updateClimaCiudades()
                adapter.submitList(it)
            }
        })

        climaActualViewModel.showToastEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Toast.makeText(context,climaActualViewModel.toastEventText.value, Toast.LENGTH_SHORT).show()
                climaActualViewModel.doneShowingToast()
            }
        })

        climaActualViewModel.showAddCiudadDialog.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                agregarCiudad()
                climaActualViewModel.doneShowingCiudadDialog()
            }
        })

        climaActualViewModel.closeDialogCiudad.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                alertDialogCiudad?.dismiss()
                climaActualViewModel.doneCloseDialogCiudad()
            }
        })

        climaActualViewModel.navigateToPronosticoExtendido.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(ClimaActualFragmentDirections.actionClimaActualFragmentToPronosticoExtendidoFragment(it))
                climaActualViewModel.displayPronosticoExtendidoComplete()
            }
        })

        binding.setLifecycleOwner(this)


        return binding.root
    }

    private fun agregarCiudad(){
        val builder = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.agregar_ciudad_dialog, null)
        builder.setView(dialogView)
        alertDialogCiudad = builder.create()
        alertDialogCiudad?.show()
        dialogView.btnAceptarDialog.setOnClickListener(View.OnClickListener {
            //Si los campos no estan vacios
            if(dialogView.etCodigoPais.text.toString() != "" && dialogView.etNombreCiudad.text.toString() != ""){
                //Guardo el filtro desde el viewModel
                climaActualViewModel.nuevaCiudad(dialogView.etCodigoPais.text.toString().trim(), dialogView.etNombreCiudad.text.toString().trim())
            }
        })
        dialogView.btnCancelarDialog.setOnClickListener(View.OnClickListener {
            alertDialogCiudad?.dismiss()
        })
    }
}