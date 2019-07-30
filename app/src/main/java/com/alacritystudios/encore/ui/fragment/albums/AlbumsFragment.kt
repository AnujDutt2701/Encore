package com.alacritystudios.encore.ui.fragment.albums

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.SortOrder
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.AlbumsFragmentBinding
import com.alacritystudios.encore.ui.adapter.MediaItemsAdapter
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.alacritystudios.encore.util.UiUtil
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class AlbumsFragment : Fragment(), MediaItemsAdapter.MediaItemsAdapterOnClickListener {

    companion object {
        fun newInstance() = AlbumsFragment()
    }

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var mBindingComponent: BindingComponent

    private var mViewModel: AlbumsViewModel? = null

    internal lateinit var albumsFragmentBinding: AlbumsFragmentBinding

    private lateinit var albumsAdapter: MediaItemsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        albumsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.albums_fragment, container, false)
        setToolbarForActivity()
        setupRecyclerView()
        return albumsFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AlbumsViewModel::class.java)
        setLiveDataListeners()
        albumsFragmentBinding.srlAlbums.isRefreshing = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onItemClick(mediaItem: MediaItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setToolbarForActivity() {
        Timber.d("setToolbarForActivity()")
        (activity as AppCompatActivity).setSupportActionBar(albumsFragmentBinding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        Timber.d("setupRecyclerView()")
        var list: List<MediaItem> = ArrayList<MediaItem>()
        albumsAdapter = MediaItemsAdapter(list, mBindingComponent, this)
        albumsFragmentBinding.rvAlbums.adapter = albumsAdapter
        albumsFragmentBinding.rvAlbums.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun setLiveDataListeners() {
        Timber.d("setLiveDataListeners()")

        mViewModel?.let {
            albumsFragmentBinding.srlAlbums.setOnRefreshListener {
                (mViewModel as AlbumsViewModel).refresh()
            }
            (mViewModel as AlbumsViewModel).resultsLiveData().observe(this, Observer {
                albumsAdapter.list = it.body ?: ArrayList<MediaItem>()
                albumsAdapter.notifyDataSetChanged()
                UiUtil.setLoadingBehaviour(
                    albumsFragmentBinding.srlAlbums, albumsFragmentBinding.rvAlbums
                    , albumsFragmentBinding.clAlbums, it
                )
            })
            (mViewModel as AlbumsViewModel).updateSortOrder(SortOrder.SORT_ORDER_MEDIA_NAME_ASCENDING)
        }
    }
}