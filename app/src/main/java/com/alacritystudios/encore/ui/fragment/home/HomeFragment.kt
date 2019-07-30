package com.alacritystudios.encore.ui.fragment.home

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.encore.R
import com.alacritystudios.encore.SortOrder
import com.alacritystudios.encore.data.domain.HomeItem
import com.alacritystudios.encore.data.domain.MediaItem
import com.alacritystudios.encore.databinding.HomeFragmentBinding
import com.alacritystudios.encore.ui.adapter.HomeItemsAdapter
import com.alacritystudios.encore.ui.adapter.MediaItemsAdapter
import com.alacritystudios.encore.ui.adapter.MediaItemsGridAdapter
import com.alacritystudios.encore.ui.binding.BindingComponent
import com.alacritystudios.encore.util.UiUtil
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject
import android.view.*
import com.alacritystudios.encore.data.domain.BottomMenuItem
import com.alacritystudios.encore.data.domain.BottomSheetOptions
import com.alacritystudios.encore.ui.helper.BottomSheetMenu
import com.alacritystudios.encore.ui.helper.BottomSheetOptionsBuilder


class HomeFragment : Fragment(), HomeItemsAdapter.HomeItemsAdapterOnClickListener,
    MediaItemsGridAdapter.MediaItemsGridAdapterOnClickListener , BottomSheetOptions.BottomSheetOptionClickBehavior{

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var mBindingComponent: BindingComponent

    private var mViewModel: HomeViewModel? = null

    internal lateinit var homeFragmentBinding: HomeFragmentBinding

    private lateinit var homeItemsAdapter: HomeItemsAdapter

    private lateinit var songsAdapter: MediaItemsGridAdapter

    private val BOTTOM_SHEET_IDENTIFIER_SORT_ORDER: Int = 1


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()


        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_sort_order) {
            showSortOrderBottomSheet()
            return true
        } else if (id == R.id.action_search) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        setHasOptionsMenu(true)
        setToolbarForActivity()
        setupNavigation()
        setupRecyclerView()
        return homeFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HomeViewModel::class.java)
        setLiveDataListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onItemClick(type: HomeItem.ItemLink) {
        val navController = Navigation.findNavController(activity as Activity, R.id.fragment)
        when (type) {

            HomeItem.ItemLink.ITEM_LINK_PLAYLISTS -> {
                val bundle = Bundle()
                navController.navigate(R.id.songsFragment, bundle)
            }
            HomeItem.ItemLink.ITEM_LINK_GENRES -> {
                val bundle = Bundle()
                navController.navigate(R.id.songsFragment, bundle)
            }
            HomeItem.ItemLink.ITEM_LINK_ARTISTS -> {
                val bundle = Bundle()
                navController.navigate(R.id.artistsFragment, bundle)
            }
            HomeItem.ItemLink.ITEM_LINK_ALBUMS -> {
                val bundle = Bundle()
                navController.navigate(R.id.albumsFragment, bundle)
            }
            HomeItem.ItemLink.ITEM_LINK_SONGS -> {
                val bundle = Bundle()
                navController.navigate(R.id.songsFragment, bundle)
            }
        }
    }

    override fun onItemClick(mediaItem: MediaItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(bottomSheetOption: BottomSheetOptions.BottomSheetOption, identifier : Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setToolbarForActivity() {
        Timber.d("setToolbarForActivity()")
        (activity as AppCompatActivity).setSupportActionBar(homeFragmentBinding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = getText(R.string.home_fragment_title)
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController((activity as AppCompatActivity), R.id.fragment)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (isInternalFragment(destination.id)) {
//                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            } else {
//                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            }
//        }
    }

    private fun isInternalFragment(id: Int): Boolean {
        return id != R.id.homeFragment
    }

    private fun setupRecyclerView() {
        Timber.d("setupRecyclerView()")
        var list: List<HomeItem> = arrayListOf(
            HomeItem(HomeItem.ItemLink.ITEM_LINK_PLAYLISTS, getString(R.string.home_link_label_playlists)),
            HomeItem(HomeItem.ItemLink.ITEM_LINK_GENRES, getString(R.string.home_link_label_genres)),
            HomeItem(HomeItem.ItemLink.ITEM_LINK_ARTISTS, getString(R.string.home_link_label_artists)),
            HomeItem(HomeItem.ItemLink.ITEM_LINK_ALBUMS, getString(R.string.home_link_label_albums)),
            HomeItem(HomeItem.ItemLink.ITEM_LINK_SONGS, getString(R.string.home_link_label_songs))
        )
        homeItemsAdapter = HomeItemsAdapter(list, mBindingComponent, this)
        homeFragmentBinding.rvHomeItems.adapter = homeItemsAdapter
        homeFragmentBinding.rvHomeItems.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        songsAdapter = MediaItemsGridAdapter(
            ArrayList<MediaItem>(),
            mBindingComponent,
            this
        )

        homeFragmentBinding.rvRecentlyAdded.adapter = songsAdapter
        homeFragmentBinding.rvRecentlyAdded.layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
    }

    private fun setLiveDataListeners() {
        Timber.d("setLiveDataListeners()")


        mViewModel?.let {
            homeFragmentBinding.srlRecentlyAdded.setOnRefreshListener {
                (mViewModel as HomeViewModel).refresh()
            }
            (mViewModel as HomeViewModel).resultsLiveData().observe(this, Observer {
                songsAdapter.list = it.body ?: ArrayList<MediaItem>()
                songsAdapter.notifyDataSetChanged()
                UiUtil.setLoadingBehaviour(
                    homeFragmentBinding.srlRecentlyAdded, homeFragmentBinding.rvRecentlyAdded
                    , homeFragmentBinding.clRecentlyAdded, it
                )
            })
            (mViewModel as HomeViewModel).updateSortOrder(SortOrder.SORT_ORDER_MEDIA_NAME_ASCENDING)
        }
    }

    private fun showSortOrderBottomSheet() {
        val items = arrayListOf(
            BottomSheetOptions.BottomSheetOption(R.drawable.ic_headset_24dp, getString(R.string.sort_order_artists), {  }),
            BottomSheetOptions.BottomSheetOption(R.drawable.ic_headset_24dp, getString(R.string.sort_order_songs), {  }))
        BottomSheetOptionsBuilder((activity as Context), BottomSheetOptions(BOTTOM_SHEET_IDENTIFIER_SORT_ORDER, getString(R.string.sort_order_title), 200, items, this), mBindingComponent).show()
    }
}
