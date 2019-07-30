package com.alacritystudios.encore.ui.fragment.artists

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
import com.alacritystudios.encore.Song
import com.alacritystudios.encore.SortOrder
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.ArtistsFragmentBinding
import com.alacritystudios.encore.ui.adapter.MediaItemsAdapter
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.alacritystudios.encore.util.UiUtil
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

class ArtistsFragment : Fragment(), MediaItemsAdapter.MediaItemsAdapterOnClickListener {

    companion object {
        fun newInstance() = ArtistsFragment()
    }

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var mBindingComponent: BindingComponent

    private var mViewModel: ArtistsViewModel? = null

    internal lateinit var artistsFragmentBinding: ArtistsFragmentBinding

    private lateinit var artistsAdapter: MediaItemsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        artistsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.artists_fragment, container, false)
        setToolbarForActivity()
        setupRecyclerView()
        return artistsFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ArtistsViewModel::class.java)
        setLiveDataListeners()
        artistsFragmentBinding.srlArtists.isRefreshing = true
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
        (activity as AppCompatActivity).setSupportActionBar(artistsFragmentBinding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        Timber.d("setupRecyclerView()")
        var list: List<MediaItem> = ArrayList<MediaItem>()
        artistsAdapter = MediaItemsAdapter(list, mBindingComponent, this)
        artistsFragmentBinding.rvArtists.adapter = artistsAdapter
        artistsFragmentBinding.rvArtists.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun setLiveDataListeners() {
        Timber.d("setLiveDataListeners()")

        mViewModel?.let {
            artistsFragmentBinding.srlArtists.setOnRefreshListener {
                (mViewModel as ArtistsViewModel).refresh()
            }
            (mViewModel as ArtistsViewModel).resultsLiveData().observe(this, Observer {
                artistsAdapter.list = it.body ?: ArrayList<MediaItem>()
                artistsAdapter.notifyDataSetChanged()
                UiUtil.setLoadingBehaviour(
                    artistsFragmentBinding.srlArtists, artistsFragmentBinding.rvArtists
                    , artistsFragmentBinding.clArtists, it
                )
            })
            (mViewModel as ArtistsViewModel).updateSortOrder(SortOrder.SORT_ORDER_MEDIA_NAME_ASCENDING)
        }
    }
}