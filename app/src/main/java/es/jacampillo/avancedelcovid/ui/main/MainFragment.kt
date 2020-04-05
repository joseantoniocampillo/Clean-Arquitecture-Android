package es.jacampillo.avancedelcovid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val viewmodel : MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        val factory = MainViewModel.Factory(activity.application)
        ViewModelProvider(this, factory)
            .get(MainViewModel::class.java)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewmodel.paises.observe(viewLifecycleOwner, Observer {
//            viewModelAdapter?.submitList(it)
//        })
//    }
//
//    private var viewModelAdapter: PaisesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodelo = viewmodel
        binding.paisesRecyclerView.adapter = PaisesAdapter()

        return binding.root
    }
}
