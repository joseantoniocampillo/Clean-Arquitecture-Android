package es.jacampillo.avancedelcovid.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.jacampillo.avancedelcovid.databinding.PaisViewItemBinding
import es.jacampillo.avancedelcovid.models_api_response.Pais

class PaisesAdapter : ListAdapter<Pais, PaisesAdapter.PaisViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        return PaisViewHolder(PaisViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val pais = getItem(position)
        holder.bind(pais)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Pais>(){
        override fun areItemsTheSame(oldItem: Pais, newItem: Pais): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Pais, newItem: Pais): Boolean {
            return oldItem.country == newItem.country
        }
    }

    class PaisViewHolder(private val binding: PaisViewItemBinding) : RecyclerView.ViewHolder (binding.root){
        fun bind(pais: Pais){
            binding.pais = pais
            binding.executePendingBindings()
        }
    }

}