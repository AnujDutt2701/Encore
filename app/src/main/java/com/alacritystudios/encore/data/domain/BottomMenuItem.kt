package com.alacritystudios.encore.data.domain

data class BottomMenuItem(
    val resId: Int,
    val name: String,
    val action: () -> Unit
)