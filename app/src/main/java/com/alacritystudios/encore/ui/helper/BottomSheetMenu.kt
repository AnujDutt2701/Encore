package com.alacritystudios.encore.ui.helper

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.data.domain.BottomMenuItem
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.MediaItemMenuBinding
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.media_item_menu.view.*

class BottomSheetMenu(
    private val context: Context,
    private val mediaItem: MediaItem,
    var bindingComponent: BindingComponent,
    private val items: List<BottomMenuItem>
) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {

        val view = DataBindingUtil.inflate<MediaItemMenuBinding>(LayoutInflater.from(context), R.layout.media_item_menu, null, false, bindingComponent)
        bottomSheetDialog.setContentView(view.root)


        with(view.root) {
            view.mediaItem = mediaItem
            iv_media_art.clipToOutline = true
            rv_bottom_sheet_menu.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rv_bottom_sheet_menu.adapter = BottomSheetMenuAdapter(items)
        }
    }

    fun show() {
        bottomSheetDialog.show()
    }
}