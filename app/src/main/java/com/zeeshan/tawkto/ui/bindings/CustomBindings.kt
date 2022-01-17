package com.zeeshan.tawkto.ui.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zeeshan.tawkto.musicplayer.R

object CustomBindings {

    @JvmStatic
    @BindingAdapter("bindAvatar")
    fun bindAvatar(imageView: ImageView, url: String) {
        try {
            Glide.with(imageView.context).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}