package com.alacritystudios.encore.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.alacritystudios.encore.R
import com.bumptech.glide.Glide

class CommonBindingUtils {

    companion object {

        @BindingAdapter("imageUrl")
        fun setImageUrl(view: ImageView, url: String) {
            Glide.with(view).load(url).centerCrop().error(R.drawable.ic_headset_24dp).into(view)
        }
    }

}