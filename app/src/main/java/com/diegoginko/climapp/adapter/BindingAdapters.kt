package com.diegoginko.climapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.diegoginko.climapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)

    }
}

@BindingAdapter("fabVisibility")
fun fabVisibility(view: FloatingActionButton, listSize: Int?){
    listSize?.let {
        if(it < 6){
            view.visibility = View.VISIBLE
        }else{
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("onLongClick")
fun setOnLongClickListener(view: View, listener: Runnable) {
    view.setOnLongClickListener { listener.run(); true }
}
