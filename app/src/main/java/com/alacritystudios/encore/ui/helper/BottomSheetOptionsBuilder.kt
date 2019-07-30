package com.alacritystudios.encore.ui.helper

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.data.domain.BottomMenuItem
import com.alacritystudios.encore.data.domain.BottomSheetOptions
import com.alacritystudios.encore.databinding.BsListLayoutBinding
import com.alacritystudios.encore.databinding.MediaItemMenuBinding
import com.alacritystudios.encore.ui.adapter.BottomSheetOptionsAdapter
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.media_item_menu.view.*

class BottomSheetOptionsBuilder(
    private val context: Context,
    private val options: BottomSheetOptions,
    var bindingComponent: BindingComponent
) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {

        val view = DataBindingUtil.inflate<BsListLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.bs_list_layout,
            null,
            false,
            bindingComponent
        )
        bottomSheetDialog.setContentView(view.root)

        with(view.root) {
            view.title = options.message
            rv_bottom_sheet_menu.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rv_bottom_sheet_menu.adapter = BottomSheetOptionsAdapter(options, bindingComponent)
        }
    }

    fun show() {
        bottomSheetDialog.show()
    }
}