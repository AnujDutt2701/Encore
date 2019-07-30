package com.alacritystudios.encore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.data.domain.BottomSheetOptions
import com.alacritystudios.encore.databinding.RvBsOptionsItemBinding
import com.alacritystudios.encore.ui.binding.BindingComponent
import kotlinx.android.synthetic.main.rv_bs_options_item.view.*

class BottomSheetOptionsAdapter(
    private val options: BottomSheetOptions,
    private val bindingComponent: BindingComponent
) :
    RecyclerView.Adapter<BottomSheetOptionsAdapter.BottomSheetOptionsAdapterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetOptionsAdapterViewHolder {
        return BottomSheetOptionsAdapterViewHolder(
            DataBindingUtil.inflate<RvBsOptionsItemBinding>(
                LayoutInflater.from(parent.context), R.layout.rv_bs_options_item,
                parent, false, bindingComponent
            )
        )
    }

    override fun getItemCount(): Int = options.list.size

    override fun onBindViewHolder(holder: BottomSheetOptionsAdapterViewHolder, position: Int) {
        holder.bind(options.identifier, options.list[position], options.itemClickListener)
    }

    class BottomSheetOptionsAdapterViewHolder(val rvBsOptionsItemBinding: RvBsOptionsItemBinding) :
        RecyclerView.ViewHolder(rvBsOptionsItemBinding.root) {

        fun bind(
            identifier: Int,
            item: BottomSheetOptions.BottomSheetOption,
            onclickListener: BottomSheetOptions.BottomSheetOptionClickBehavior
        ) {
            rvBsOptionsItemBinding.option = item
            rvBsOptionsItemBinding.root.setOnClickListener {
                onclickListener.onItemClick(item, identifier)
            }
        }
    }

}