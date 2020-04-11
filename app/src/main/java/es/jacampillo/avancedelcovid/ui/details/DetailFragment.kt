package es.jacampillo.avancedelcovid.ui.details

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import es.jacampillo.avancedelcovid.R
import es.jacampillo.avancedelcovid.databinding.DetailFragmentBinding
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.ui.main.MainViewModel

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DetailFragmentBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        val application = requireNotNull(activity).application
        val pais = DetailFragmentArgs.fromBundle(requireArguments()).pais
        activity?.title = pais.country

        val factory = DetailViewModel.Factory(pais, application)
        val viewmodel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        binding.vm = viewmodel

        return binding.root
    }


}
