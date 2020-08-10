package com.diegoginko.climapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diegoginko.climapp.databinding.ExtendidoItemBinding
import com.diegoginko.climapp.network.Extendido

class ExtendidoAdapter(val clickListener: ExtendidoClickListener): ListAdapter<Extendido, ExtendidoAdapter.ViewHolder>(ExtendidoDiffCallback()){

    override fun onBindViewHolder(holder: ExtendidoAdapter.ViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtendidoAdapter.ViewHolder {
        return ExtendidoAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ExtendidoItemBinding): RecyclerView.ViewHolder(binding.root){
        //Asocio el objeto a la vista
        fun bind(clickListener: ExtendidoClickListener, item : Extendido){
            binding.extendido = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ExtendidoAdapter.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExtendidoItemBinding.inflate(layoutInflater, parent, false)
                return ExtendidoAdapter.ViewHolder(binding)
            }
        }
    }

}



private class ExtendidoDiffCallback : DiffUtil.ItemCallback<Extendido>(){
    override fun areItemsTheSame(oldItem: Extendido, newItem: Extendido): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: Extendido, newItem: Extendido): Boolean {
        return oldItem == newItem
    }

}

class ExtendidoClickListener(val clickListener: (extendido: Extendido) -> Unit){
    fun onClick(extendido: Extendido) = clickListener(extendido)
}