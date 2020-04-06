package es.jacampillo.avancedelcovid.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.R
import es.jacampillo.avancedelcovid.databinding.MainFragmentBinding
import es.jacampillo.avancedelcovid.toDateFormat

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

        viewmodel.paisesOrdenados.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size>0){
                activity?.title = it.get(0).updated?.toDateFormat()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_principal, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_actualizar -> viewmodel.listanormal();
            R.id.item_ord_fallecidos -> {
                viewmodel.lista2()
                activity?.title = viewmodel.paisesOrdenados.value?.get(0)?.updated?.toDateFormat() + " Ord Fallecidos"
            }
        }
        return true
    }
}


