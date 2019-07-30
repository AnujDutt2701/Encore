package com.alacritystudios.encore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.data.domain.HomeItem
import com.alacritystudios.encore.databinding.RvHomeItemsBinding
import com.alacritystudios.encore.ui.binding.BindingComponent


class HomeItemsAdapter : RecyclerView.Adapter<HomeItemsAdapter.HomeItemsViewHolder> {

    var list: List<HomeItem>
    var bindingComponent: BindingComponent
    var homeItemsAdapterOnClickListener: HomeItemsAdapterOnClickListener

    constructor(
        list: List<HomeItem>,
        bindingComponent: BindingComponent,
        homeItemsAdapterOnClickListener: HomeItemsAdapterOnClickListener
    ) : super() {
        this.list = list
        this.bindingComponent = bindingComponent
        this.homeItemsAdapterOnClickListener = homeItemsAdapterOnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemsViewHolder {
        val binding = DataBindingUtil.inflate<RvHomeItemsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_home_items,
            parent,
            false,
            bindingComponent
        )
        return HomeItemsViewHolder(binding, homeItemsAdapterOnClickListener)
    }

    override fun onBindViewHolder(holder: HomeItemsViewHolder, position: Int) {
        holder.bindViewToData(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface HomeItemsAdapterOnClickListener {

        fun onItemClick(type: HomeItem.ItemLink)
    }

    class HomeItemsViewHolder : RecyclerView.ViewHolder {

        var rvBinding: RvHomeItemsBinding
        var homeItemsAdapterOnClickListener: HomeItemsAdapterOnClickListener

        constructor(
            rvItemHomeItemsBinding: RvHomeItemsBinding,
            homeItemsAdapterOnClickListener: HomeItemsAdapterOnClickListener
        ) : super(rvItemHomeItemsBinding.root) {
            this.rvBinding = rvItemHomeItemsBinding
            this.homeItemsAdapterOnClickListener = homeItemsAdapterOnClickListener
        }

        fun bindViewToData(data: HomeItem): Unit {
            this.rvBinding.setItem(data)
            this.rvBinding.setOnClickListener(homeItemsAdapterOnClickListener)
        }
    }
}