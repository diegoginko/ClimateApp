package com.diegoginko.climapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diegoginko.climapp.data.Ciudad
import com.diegoginko.climapp.databinding.CiudadItemBinding

class CiudadAdapter(val clickListener: CiudadClickListener, val longClickListener: CiudadLongClickListener) : ListAdapter<Ciudad, CiudadAdapter.ViewHolder>(CiudadDiffCallback()){

    override fun onBindViewHolder(holder: CiudadAdapter.ViewHolder, position: Int) {
        holder.bind(clickListener,longClickListener,getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiudadAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CiudadItemBinding): RecyclerView.ViewHolder(binding.root){
        //Asocio el objeto a la vista
        fun bind(clickListener: CiudadClickListener, longClickListener: CiudadLongClickListener, item : Ciudad){
            binding.ciudad = item
            binding.clickListener = clickListener
            binding.longClickListener = longClickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CiudadItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}

private class CiudadDiffCallback : DiffUtil.ItemCallback<Ciudad>(){
    override fun areItemsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
        return oldItem.ciudadId == newItem.ciudadId
    }

    override fun areContentsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
        return oldItem == newItem
    }

}

class CiudadClickListener(val clickListener: (ciudad: Ciudad) -> Unit){
    fun onClick(ciudad: Ciudad) = clickListener(ciudad)
}

class CiudadLongClickListener(val clickListener: (ciudad: Ciudad) -> Unit){
    fun onClick(ciudad: Ciudad) = clickListener(ciudad)
}
