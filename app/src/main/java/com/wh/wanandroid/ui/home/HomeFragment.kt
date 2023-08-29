package com.wh.wanandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.wh.wanandroid.adapter.HomeBannerListAdapter
import com.wh.wanandroid.adapter.HomeListAdapter
import com.wh.wanandroid.adapter.HomoTopListAdapter
import com.wh.wanandroid.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val mBannerAdapter by lazy { HomeBannerListAdapter(context) }
    private val mTopListAdapter by lazy { HomoTopListAdapter(context) }
    private val mItemListAdapter by lazy {
        HomeListAdapter(context).apply {
            addLoadStateListener(::loadStateListener)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->

            _binding?.homeSmartRefresh?.setEnableLoadMore(false)
            _binding?.homeSmartRefresh?.setOnRefreshListener {
                viewModel.getBanner()
                viewModel.getHomeTopList()
                mItemListAdapter.refresh()
            }
            _binding?.homeList?.apply {
                adapter = ConcatAdapter(
                    ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
                    arrayListOf(mBannerAdapter, mTopListAdapter, mItemListAdapter)
                )
                layoutManager = LinearLayoutManager(context)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.bannerState.collect {
                            mBannerAdapter.submitList(arrayListOf(it))
                        }
                    }

                    launch {
                        viewModel.topState.collect {
                            mTopListAdapter.submitList(it)
                        }
                    }

                    launch {
                        viewModel.pager.distinctUntilChanged().collect {
                            mItemListAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    private fun loadStateListener(it: CombinedLoadStates) {
        when (it.refresh) {
            is LoadState.Loading -> {
            }
            is LoadState.NotLoading -> {
                _binding?.homeSmartRefresh?.finishRefresh(true)
            }
            is LoadState.Error -> {
                _binding?.homeSmartRefresh?.finishRefresh(false)
            }
        }
    }
}