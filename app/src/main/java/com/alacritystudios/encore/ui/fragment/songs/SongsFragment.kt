package com.alacritystudios.encore.ui.fragment.songs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.SortOrder
import com.alacritystudios.encore.data.domain.BottomMenuItem
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.SongsFragmentBinding
import com.alacritystudios.encore.ui.adapter.MediaItemsAdapter
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.alacritystudios.encore.ui.helper.BottomSheetMenu
import com.alacritystudios.encore.util.UiUtil
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class SongsFragment : Fragment(), MediaItemsAdapter.MediaItemsAdapterOnClickListener {

    companion object {
        fun newInstance() = SongsFragment()
    }

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var mBindingComponent: BindingComponent

    private var mViewModel: SongsViewModel? = null

    internal lateinit var songsFragmentBinding: SongsFragmentBinding

    private lateinit var songsAdapter: MediaItemsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        songsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.songs_fragment, container, false)
        setToolbarForActivity()
        setupRecyclerView()
        return songsFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SongsViewModel::class.java)
        setLiveDataListeners()
        songsFragmentBinding.srlSongs.isRefreshing = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onItemClick(mediaItem: MediaItem) {
        showMenu(mediaItem)
    }

    private fun setToolbarForActivity() {
        Timber.d("setToolbarForActivity()")
        (activity as AppCompatActivity).setSupportActionBar(songsFragmentBinding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        Timber.d("setupRecyclerView()")
        var list: List<MediaItem> = ArrayList<MediaItem>()
        songsAdapter = MediaItemsAdapter(list, mBindingComponent, this)
        songsFragmentBinding.rvSongs.adapter = songsAdapter
        songsFragmentBinding.rvSongs.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun setLiveDataListeners() {
        Timber.d("setLiveDataListeners()")

        mViewModel?.let {
            songsFragmentBinding.srlSongs.setOnRefreshListener {
                (mViewModel as SongsViewModel).refresh()
            }
            (mViewModel as SongsViewModel).resultsLiveData().observe(this, Observer {
                songsAdapter.list = it.body ?: ArrayList<MediaItem>()
                songsAdapter.notifyDataSetChanged()
                UiUtil.setLoadingBehaviour(
                    songsFragmentBinding.srlSongs, songsFragmentBinding.rvSongs
                    , songsFragmentBinding.clSongs, it
                )
            })
            (mViewModel as SongsViewModel).updateSortOrder(SortOrder.SORT_ORDER_MEDIA_NAME_ASCENDING)
        }
    }

    fun showMenu(mediaItem: MediaItem) {
        val items = arrayListOf(
            BottomMenuItem(R.drawable.ic_headset_24dp, "Edit", {
                // edit action
            }),
            BottomMenuItem(R.drawable.ic_headset_24dp, "Delete", {
                // delete action
            })
        )
        BottomSheetMenu((activity
                 as Context), mediaItem, mBindingComponent, items)
            .show()
    }
}