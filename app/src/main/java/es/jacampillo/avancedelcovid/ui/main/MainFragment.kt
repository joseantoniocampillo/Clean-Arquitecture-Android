package es.jacampillo.avancedelcovid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val viewmodel : MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodelo = viewmodel
        binding.paisesRecyclerView.adapter = PaisesAdapter()

        return binding.root
    }
}
