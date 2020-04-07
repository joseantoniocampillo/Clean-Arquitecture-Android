package es.jacampillo.avancedelcovid.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.R
import es.jacampillo.avancedelcovid.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val viewmodel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        val factory = MainViewModel.Factory(activity.application)
        ViewModelProvider(this, factory)
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodelo = viewmodel
        binding.paisesRecyclerView.adapter = PaisesAdapter()

        viewmodel.titulo.observe(viewLifecycleOwner, Observer {activity?.title = it})

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_principal, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            viewmodel.updateSelection(item.itemId)

        return true
    }
}


