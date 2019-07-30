package com.alacritystudios.encore.data.domain

import androidx.annotation.IntegerRes

class BottomSheetOptions {

    var identifier: Int
    var message: String
    var size: Int
    var list: List<BottomSheetOption>
    var itemClickListener: BottomSheetOptionClickBehavior

    constructor(
        identifier: Int,
        message: String,
        size: Int,
        list: List<BottomSheetOption>,
        itemClickListener: BottomSheetOptionClickBehavior
    ) {
        this.identifier = identifier
        this.message = message
        this.size = size
        this.list = list
        this.itemClickListener = itemClickListener
    }


    class BottomSheetOption {

        var drawableInt: Int
        var message: String
        var clickBehavior : () -> Unit

        constructor(drawableInt: Int, message: String, clickBehavior: () -> Unit) {
            this.drawableInt = drawableInt
            this.message = message
            this.clickBehavior = clickBehavior
        }
    }

    interface BottomSheetOptionClickBehavior {

        fun onItemClick(bottomSheetOption: BottomSheetOption, identifier : Int)
    }

}
