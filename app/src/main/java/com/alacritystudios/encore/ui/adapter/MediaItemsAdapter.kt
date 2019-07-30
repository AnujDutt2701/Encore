package com.alacritystudios.encore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.RvMediaItemBinding
import com.alacritystudios.encore.databinding.RvMediaItemGridBinding
import com.alacritystudios.encore.ui.binding.BindingComponent

class MediaItemsAdapter : RecyclerView.Adapter<MediaItemsAdapter.MediaItemsViewHolder> {

    var list: List<MediaItem>
    var bindingComponent: BindingComponent
    var mediaItemsAdapterOnClickListener: MediaItemsAdapterOnClickListener

    constructor(
        list: List<MediaItem>,
        bindingComponent: BindingComponent,
        mediaItemsAdapterOnClickListener: MediaItemsAdapterOnClickListener
    ) : super() {
        this.list = list
        this.bindingComponent = bindingComponent
        this.mediaItemsAdapterOnClickListener = mediaItemsAdapterOnClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemsViewHolder {
        val binding = DataBindingUtil.inflate<RvMediaItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_media_item,
            parent,
            false,
            bindingComponent
        )

        return MediaItemsViewHolder(binding, mediaItemsAdapterOnClickListener)
    }

    override fun onBindViewHolder(holder: MediaItemsViewHolder, position: Int) {
        holder.bindViewToData(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MediaItemsAdapterOnClickListener {

        fun onItemClick(mediaItem: MediaItem)
    }

    class MediaItemsViewHolder : RecyclerView.ViewHolder {

        var rvBinding: RvMediaItemBinding
        var mediaItemsAdapterOnClickListener: MediaItemsAdapterOnClickListener

        constructor(
            rvBinding: RvMediaItemBinding,
            mediaItemsAdapterOnClickListener: MediaItemsAdapterOnClickListener
        ) : super(rvBinding.root) {
            this.rvBinding = rvBinding
            this.mediaItemsAdapterOnClickListener = mediaItemsAdapterOnClickListener
        }

        fun bindViewToData(mediaItem: MediaItem): Unit {
            this.rvBinding.ivMediaArt.clipToOutline = true
            this.rvBinding.mediaItem = mediaItem
            this.rvBinding.onClickListener = mediaItemsAdapterOnClickListener
        }
    }
}