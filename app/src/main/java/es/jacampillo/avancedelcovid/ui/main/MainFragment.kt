package es.jacampillo.avancedelcovid.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import es.jacampillo.avancedelcovid.R
import es.jacampillo.avancedelcovid.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        val FALLECIDOS = R.id.item_ord_fallecidos
        val POSITIVOS = R.id.item_actualizar
        val FALLECIDOS_HOY = R.id.item_ord_fallecidos_hoy
        val RECUPERADOS = R.id.item_ord_recuperados
        val GRAVES = R.id.item_ord_graves
        val TEST = R.id.item_ord_test
        val TEST_POR_MILLON = R.id.item_ord_test_por_millon

        // para item en recyclerview
        var selected = 0
    }

    init {

    }

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
        binding.paisesRecyclerView.adapter = PaisesAdapter(PaisListener {
            viewmodel.navegahaciaFuncion(it)
        })

        viewmodel.navegahacia.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(
                        it
                    )
                )
                viewmodel.navegacionCompletada()
            }
        })

        viewmodel.titulo.observe(viewLifecycleOwner, Observer { activity?.title = it })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_principal, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // valor recuperado en la vista de items para renderizar cambios
        if (item.itemId == R.id.item_actualizar_fecha) {
            if (isOnline(activity!!.applicationContext)) {
                viewmodel.updateVersion()
                viewmodel.updateSelection(selected)
            } else {
                Snackbar.make(this.requireView(), "No hay acceso a la red", Snackbar.LENGTH_LONG)
                    .show()
            }

        } else {
            selected = item.itemId
            viewmodel.updateSelection(item.itemId)
        }
        return true
    }

    @Suppress("DEPRECATION")
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}


