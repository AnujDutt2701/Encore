package com.alacritystudios.encore.ui.binding

import androidx.databinding.DataBindingComponent

class BindingComponent : DataBindingComponent {

    override fun getCompanion(): CommonBindingUtils.Companion {
        return CommonBindingUtils
    }
}