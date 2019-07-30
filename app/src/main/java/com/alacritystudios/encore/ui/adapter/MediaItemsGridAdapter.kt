package com.alacritystudios.encore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.RvMediaItemGridBinding
import com.alacritystudios.encore.ui.binding.BindingComponent

class MediaItemsGridAdapter : RecyclerView.Adapter<MediaItemsGridAdapter.MediaItemsGridViewHolder> {

    var list: List<MediaItem>
    var bindingComponent: BindingComponent
    var mediaItemsGridAdapterOnClickListener: MediaItemsGridAdapterOnClickListener

    constructor(
        list: List<MediaItem>,
        bindingComponent: BindingComponent,
        mediaItemsAdapterOnClickListener: MediaItemsGridAdapterOnClickListener
    ) : super() {
        this.list = list
        this.bindingComponent = bindingComponent
        this.mediaItemsGridAdapterOnClickListener = mediaItemsAdapterOnClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemsGridViewHolder {
        val binding = DataBindingUtil.inflate<RvMediaItemGridBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_media_item_grid,
            parent,
            false,
            bindingComponent
        )

        return MediaItemsGridViewHolder(binding, mediaItemsGridAdapterOnClickListener)
    }

    override fun onBindViewHolder(holder: MediaItemsGridViewHolder, position: Int) {
        holder.bindViewToData(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MediaItemsGridAdapterOnClickListener {

        fun onItemClick(mediaItem: MediaItem)
    }

    class MediaItemsGridViewHolder : RecyclerView.ViewHolder {

        var rvBinding: RvMediaItemGridBinding
        var mediaItemsGridAdapterOnClickListener: MediaItemsGridAdapterOnClickListener

        constructor(
            rvBinding: RvMediaItemGridBinding,
            mediaItemsAdapterOnClickListener: MediaItemsGridAdapterOnClickListener
        ) : super(rvBinding.root) {
            this.rvBinding = rvBinding
            this.mediaItemsGridAdapterOnClickListener = mediaItemsAdapterOnClickListener
        }

        fun bindViewToData(mediaItem: MediaItem): Unit {
            this.rvBinding.ivMediaArt.clipToOutline = true
            this.rvBinding.mediaItem = mediaItem
            this.rvBinding.onClickListener = mediaItemsGridAdapterOnClickListener
        }
    }
}